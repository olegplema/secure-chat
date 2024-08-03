
package com.plema.user.services;

import java.util.List;

import com.plema.user.entities.User;
import com.plema.user.repositories.UserRepository;
import com.plema.user.services.interfaces.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class UserServiceIpml implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getUsers(int limit, int offset) {
        int pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent(); 
    }

}
