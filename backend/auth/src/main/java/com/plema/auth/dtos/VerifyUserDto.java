package com.plema.auth.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyUserDto {
    @NotEmpty(message = "Public key can not be empty")
    private String pubKey;

    @NotEmpty(message = "Token can not be empty")
    private String token;
}
