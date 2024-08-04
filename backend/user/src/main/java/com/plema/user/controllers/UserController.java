package com.plema.user.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plema.user.dtos.UserDto;
import com.plema.user.entities.User;
import com.plema.user.mappers.UserDtoMapper;
import com.plema.user.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @GetMapping
    public HttpEntity<List<UserDto>> getUsers(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset) {
        List<User> users = userService.getUsers(limit, offset);
        List<UserDto> response = userDtoMapper.toDto(users);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @PatchMapping("/update")
    public HttpEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto) {
        User mappedUser = userDtoMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(mappedUser);
        UserDto response = userDtoMapper.toDto(updatedUser);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<UserDto> removeUserById(@PathVariable(name = "id") UUID id) {
        User deletedUser = userService.removeUser(id);
        UserDto response = userDtoMapper.toDto(deletedUser);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

}
