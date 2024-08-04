package com.plema.user.dtos;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "Id can not be empty")
    @UUID(message = "Id is not valid")
    private String id;

    @NotEmpty(message = "Username can not be empty")
    @Size(min = 4, max = 20, message = "Username size must be between {min} and {max}")
    private String username;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Public key can not be empty")
    private String pubKey;
}
