package com.plema.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private UserDto user;
    private String accessToken;
    private String refreshToken;
}
