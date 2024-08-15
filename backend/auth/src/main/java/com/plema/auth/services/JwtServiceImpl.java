package com.plema.auth.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.plema.auth.services.interfaces.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-expiration-time}")
    private long jwtAccessExpiration;

    @Value("${security.jwt.email-expiration-time}")
    private long jwtRefreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(String email, String username) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", email);
        return generateToken(extraClaims, username, jwtAccessExpiration);
    }

    public String generateRefreshToken(String email, String username) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", email);
        return generateToken(extraClaims, username, jwtRefreshExpiration);
    }

    public String generateEmailToken(UUID userId) throws Exception {
        return Jwts.builder()
                .claim("id", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private String generateToken(Map<String, Object> extraClaims, String username, long expiration) {
        return buildToken(extraClaims, username, expiration);
    }

    private String buildToken(
            Map<String, Object> claims,
            String username,
            long expiration) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7);
        return null;
    }

}
