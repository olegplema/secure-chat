package com.plema.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterEmailRequestDto {

    @NotEmpty(message = "Username can not be empty")
    @Size(min = 4, max = 20, message = "Username size must be between {min} and {max}")
    private String username;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email is not valid")
    private String email;

}
