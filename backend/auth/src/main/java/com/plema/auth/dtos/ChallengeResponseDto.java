package com.plema.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChallengeResponseDto {

    private String challenge;
}
