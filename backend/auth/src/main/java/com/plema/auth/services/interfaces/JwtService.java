package com.plema.auth.services.interfaces;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(String email, String username);

    String generateRefreshToken(String email, String username);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getToken(String getToken);

    String generateEmailToken(UUID userId) throws Exception;

    Claims extractAllClaims();
}
