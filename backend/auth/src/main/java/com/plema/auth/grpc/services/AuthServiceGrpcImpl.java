package com.plema.auth.grpc.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.plema.auth.services.interfaces.AuthService;
import com.plema.auth.services.interfaces.JwtService;
import com.plema.grpc.auth.AuthServiceGrpc.AuthServiceImplBase;
import com.plema.grpc.auth.ValidateTokenRequest;
import com.plema.grpc.auth.ValidateTokenResponse;

import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Claims;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthServiceGrpcImpl extends AuthServiceImplBase {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Override
    public void validateToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        boolean isValid = authService.isUserAuthenticated(request.getToken());
        Claims claims = jwtService.extractAllClaims(request.getToken());
        UUID id = claims.get("id", UUID.class);

        ValidateTokenResponse response = ValidateTokenResponse.newBuilder().setIsValid(isValid).setUserId(id.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
