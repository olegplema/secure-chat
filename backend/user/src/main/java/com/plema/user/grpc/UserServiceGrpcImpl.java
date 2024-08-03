package com.plema.user.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.plema.user.entities.User;
import com.plema.user.mappers.UserRequestMapper;
import com.plema.user.mappers.UserResponseMapper;
import com.plema.user.services.interfaces.UserService;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import com.plema.grpc.user.CreateUserRequest;
import com.plema.grpc.user.CreateUserResponse;
import com.plema.grpc.user.UserServiceGrpc.UserServiceImplBase;
import io.grpc.Status;

@GrpcService
public class UserServiceGrpcImpl extends UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRequestMapper requestMapper;

    @Autowired
    private UserResponseMapper responseMapper;

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        try {
            User user = requestMapper.toEntity(request);
            User createdUser = userService.createUser(user);

            responseObserver.onNext(responseMapper.toDto(createdUser));
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.ALREADY_EXISTS.withDescription(e.getReason());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
