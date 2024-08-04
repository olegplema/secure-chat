package com.plema.user.services.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.web.server.ResponseStatusException;

import com.plema.user.entities.User;

public interface UserService {

    List<User> getUsers(int limit, int offset);

    User createUser(User user) throws ResponseStatusException;

    User updateUser(User user) throws ResponseStatusException;

    User removeUser(UUID id) throws ResponseStatusException;

    User getById(UUID id);
}
