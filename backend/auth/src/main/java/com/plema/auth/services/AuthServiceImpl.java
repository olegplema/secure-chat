package com.plema.auth.services;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plema.auth.dtos.AuthResponseDto;
import com.plema.auth.dtos.UserDto;
import com.plema.auth.grpc.clients.interfaces.UserClient;
import com.plema.auth.services.interfaces.AuthService;
import com.plema.auth.services.interfaces.JwtService;

import io.jsonwebtoken.Claims;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserClient client;

    @Value("${app.email.url}")
    private String emailUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean isUserAuthenticated(String bearerToken) {
        String token = jwtService.getToken(bearerToken);

        if (token == null) {
            return false;
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return jwtService.isTokenValid(token, userDetails);
    }

    public String createChallenge(String email) throws Exception {
        UserDto user = client.getUserByEmail(email);
        UUID userId = UUID.fromString(user.getId());
        return generateChallenge(userId, user.getPubKey());
    }

    public void sendAuthEmail(String email, String username) {
        boolean isUsernameTaken = client.isFieldTaken("username", username);
        boolean isEmailTaken = client.isFieldTaken("email", email);

        if (isUsernameTaken || isEmailTaken) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already exists");
        }

        String token = jwtService.generateRefreshToken(email, username);
        sendEmail(email, token);
    }

    @Override
    public AuthResponseDto verifyUser(String token, String pubKey) {
        String username = jwtService.extractUsername(token);
        Claims claims = jwtService.extractAllClaims(token);
        String email = claims.get("email", String.class);

        UserDto user = client.createUser(username, email, pubKey);
        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getUsername(),
                UUID.fromString(user.getId()));
        String refreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getUsername());

        return AuthResponseDto.builder().user(user).accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private void sendEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Follow the link for auth");
        mailMessage.setText("Click here: " + emailUrl + token);
        javaMailSender.send(mailMessage);
    }

    private String generateChallenge(UUID userId, String pubKey) throws Exception {
        PublicKey publicKey = getPublicKey(pubKey);
        String token = jwtService.generateEmailToken(userId);

        byte[] encryptedChallenge = encryptWithPublicKey(token, publicKey);
        return Base64.getEncoder().encodeToString(encryptedChallenge);
    }

    private PublicKey getPublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private byte[] encryptWithPublicKey(String token, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(token.getBytes());
    }

}
