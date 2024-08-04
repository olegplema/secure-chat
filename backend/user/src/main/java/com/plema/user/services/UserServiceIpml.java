
package com.plema.user.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.plema.user.entities.User;
import com.plema.user.repositories.UserRepository;
import com.plema.user.services.interfaces.UserService;

public class UserServiceIpml implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getUsers(int limit, int offset) {
        int pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    @Override
    public User createUser(User user) throws ResponseStatusException {
        if (isUsernameTaken(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }

        if (isEmailTaken(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }

        return userRepository.save(user);
    }

    private boolean isEmailTaken(String email) {
        Optional<User> user = userRepository.findOneByEmail(email);

        if (user.isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean isUsernameTaken(String username) {
        Optional<User> user = userRepository.findOneByUsername(username);

        if (user.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public User getById(UUID id) throws ResponseStatusException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return userOptional.get();
    }

    @Override
    public User updateUser(User user) throws ResponseStatusException {
        Optional<User> foundUser = userRepository.findById(user.getId());

        if (foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (isUsernameTaken(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }

        if (isEmailTaken(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }

        return userRepository.save(user);
    }

	@Override
	public User removeUser(UUID id) throws ResponseStatusException {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userRepository.deleteById(foundUser.get().getId());

        return foundUser.get();
	}

}
