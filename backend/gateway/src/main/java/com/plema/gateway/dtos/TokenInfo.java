package com.plema.gateway.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {

    private String userId;
    private Boolean isTokenValid;
    
}
