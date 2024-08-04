package com.plema.user.grpc;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.plema.grpc.user.CreateUserRequest;
import com.plema.grpc.user.GetUserByIdRequest;
import com.plema.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.plema.grpc.user.UserResponse;
import com.plema.user.entities.User;
import com.plema.user.mappers.CreateUserRequestMapper;
import com.plema.user.mappers.CreateUserResponseMapper;
import com.plema.user.services.interfaces.UserService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserServiceGrpcImpl extends UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Autowired
    private CreateUserRequestMapper requestMapper;

    @Autowired
    private CreateUserResponseMapper responseMapper;

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
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

    @Override
    public void getUserById(GetUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            User foundUser = userService.getById(id);

            responseObserver.onNext(responseMapper.toDto(foundUser));
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getReason());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

}
