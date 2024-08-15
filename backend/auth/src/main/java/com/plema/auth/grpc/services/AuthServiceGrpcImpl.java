package com.plema.auth.grpc.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.plema.auth.services.interfaces.AuthService;
import com.plema.grpc.auth.AuthServiceGrpc.AuthServiceImplBase;
import com.plema.grpc.auth.ValidateTokenRequest;
import com.plema.grpc.auth.ValidateTokenResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthServiceGrpcImpl extends AuthServiceImplBase {

    @Autowired
    private AuthService authService;

    @Override
    public void validateToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        boolean isValid = authService.isUserAuthenticated(request.getAccessToken());

        ValidateTokenResponse response = ValidateTokenResponse.newBuilder().setIsValid(isValid).build(); 
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
