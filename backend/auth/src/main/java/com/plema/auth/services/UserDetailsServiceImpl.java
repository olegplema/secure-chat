package com.plema.auth.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.plema.auth.dtos.UserDto;
import com.plema.auth.grpc.clients.interfaces.UserClient;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto user = userClient.getUserByUsername(username);
        return new User(user.getUsername(), "", new ArrayList<>());
    }

}
