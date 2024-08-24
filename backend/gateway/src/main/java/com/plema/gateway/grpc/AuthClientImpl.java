package com.plema.gateway.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plema.gateway.dtos.TokenInfo;
import com.plema.gateway.grpc.interfaces.AuthClient;
import com.plema.gateway.mappers.GrpcStatusMapper;
import com.plema.grpc.auth.AuthServiceGrpc.AuthServiceBlockingStub;
import com.plema.grpc.auth.ValidateTokenRequest;
import com.plema.grpc.auth.ValidateTokenResponse;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class AuthClientImpl implements AuthClient {

    @GrpcClient("AuthService")
    private AuthServiceBlockingStub blockingStub;

    @Autowired
    private GrpcStatusMapper statusMapper;

    @Override
    public TokenInfo isTokenValid(String token) {
        ValidateTokenRequest request = ValidateTokenRequest.newBuilder().setToken(token).build();
        try {
            ValidateTokenResponse info = blockingStub.validateToken(request);
            return TokenInfo.builder().isTokenValid(info.getIsValid()).userId(info.getUserId()).build();
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

}
