package com.plema.user.services.interfaces;

import java.util.List;

import com.plema.user.entities.User;

public interface UserService {

    public List<User> getUsers(int limit, int offset);
}
