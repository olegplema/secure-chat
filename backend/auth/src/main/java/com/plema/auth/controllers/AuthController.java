package com.plema.auth.controllers;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plema.auth.dtos.ChallengeRequestDto;
import com.plema.auth.dtos.ChallengeResponseDto;
import com.plema.auth.dtos.RegisterEmailRequestDto;
import com.plema.auth.dtos.RegisterEmailResonseDto;
import com.plema.auth.dtos.UserDto;
import com.plema.auth.dtos.VerifyUserDto;
import com.plema.auth.services.interfaces.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/challenge")
    public ResponseEntity<ChallengeResponseDto> createChallenge(
            @RequestBody @Valid ChallengeRequestDto challengeRequestDto) throws Exception {
        String challenge = authService.createChallenge(challengeRequestDto.getEmail());

        ChallengeResponseDto response = new ChallengeResponseDto(challenge);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @PostMapping("/register-email")
    public ResponseEntity<RegisterEmailResonseDto> sendRegisterEmail(
            @RequestBody @Valid RegisterEmailRequestDto request) {
        authService.sendAuthEmail(request.getEmail(), request.getUsername());

        RegisterEmailResonseDto resonse = new RegisterEmailResonseDto("Email sent");
        return ResponseEntity.status(HttpStatus.SC_OK).body(resonse);
    }

    @PostMapping("/verify")
    public ResponseEntity<UserDto> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
        UserDto user = authService.verifyUser(verifyUserDto.getToken(), verifyUserDto.getPubKey());

        return ResponseEntity.status(HttpStatus.SC_OK).body(user);
    }

}
