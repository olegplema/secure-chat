package com.plema.auth.dtos;

import lombok.Data;

@Data
public class AuthResponseDto {
    private UserDto user;
    private String token;
}
