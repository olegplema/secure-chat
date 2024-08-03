package com.plema.user.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plema.user.entities.User;
import com.plema.user.services.interfaces.UserService;

@RestController
public class UserController {

    private UserService userService;

    @GetMapping
    public HttpEntity<List<User>> getUsers(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        List<User> users = userService.getUsers(limit, offset);
        return ResponseEntity.status(HttpStatus.SC_OK).body(users);
    }


    @GetMapping("/find")
    public void findBy() {
    }

    @GetMapping("/istaken")
    public void checkTaken() {
    }

    @PatchMapping("/update")
    public void updateUser() {
    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable(name = "id") UUID id) {
    }

    @GetMapping("/email/{email}")
    public void getUserByEmail(@PathVariable String email) {
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable(name = "id") UUID id) {
    }

    @PostMapping
    public void createUser() {
    }
}
