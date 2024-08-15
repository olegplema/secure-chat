package com.plema.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChallengeRequestDto {

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email is not valid")
    private String email;
}
