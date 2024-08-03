package com.plema.user.services.interfaces;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.plema.user.entities.User;

public interface UserService {

    public List<User> getUsers(int limit, int offset);

    public User createUser(User user) throws ResponseStatusException;
}
