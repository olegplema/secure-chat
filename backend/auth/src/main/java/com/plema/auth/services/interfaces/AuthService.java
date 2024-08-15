package com.plema.auth.services.interfaces;

import com.plema.auth.dtos.UserDto;

public interface AuthService {

    boolean isUserAuthenticated(String bearerToken);

    String createChallenge(String email) throws Exception;

    void sendAuthEmail(String email, String username);

    UserDto verifyUser(String token, String pubKey);
}
