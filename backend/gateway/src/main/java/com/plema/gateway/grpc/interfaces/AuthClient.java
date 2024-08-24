package com.plema.gateway.grpc.interfaces;

import com.plema.gateway.dtos.TokenInfo;

public interface AuthClient {

   TokenInfo isTokenValid(String token); 
}
