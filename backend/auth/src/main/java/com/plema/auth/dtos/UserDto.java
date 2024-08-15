package com.plema.auth.dtos;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String pubKey;
}
