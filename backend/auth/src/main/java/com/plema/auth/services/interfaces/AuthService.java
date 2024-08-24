package com.plema.auth.services.interfaces;

import com.plema.auth.dtos.AuthResponseDto;

public interface AuthService {

    boolean isUserAuthenticated(String bearerToken);

    String createChallenge(String email) throws Exception;

    void sendAuthEmail(String email, String username);

    AuthResponseDto verifyUser(String token, String pubKey);
}
