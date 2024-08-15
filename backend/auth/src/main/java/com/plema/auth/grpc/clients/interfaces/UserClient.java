package com.plema.auth.grpc.clients.interfaces;

import org.springframework.web.server.ResponseStatusException;

import com.plema.auth.dtos.UserDto;

public interface UserClient {
    UserDto getUserByEmail(String id) throws ResponseStatusException;

    UserDto createUser(String username, String email, String pubKey) throws ResponseStatusException;

    UserDto getUserByUsername(String username) throws ResponseStatusException;

    boolean isFieldTaken(String field, String value);

}
