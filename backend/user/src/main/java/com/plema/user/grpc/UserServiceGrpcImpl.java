package com.plema.user.grpc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.plema.grpc.user.CreateUserRequest;
import com.plema.grpc.user.GetUserByEmailRequest;
import com.plema.grpc.user.GetUserByUsernameRequest;
import com.plema.grpc.user.GetUserByIdRequest;
import com.plema.grpc.user.IsFieldTakenRequest;
import com.plema.grpc.user.UsersResponse;
import com.plema.grpc.user.GetUsersByIdsRequest;
import com.plema.grpc.user.IsFieldTakenResponse;
import com.plema.grpc.user.UserResponse;
import com.plema.grpc.user.UserServiceGrpc.UserServiceImplBase;
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
    public void getUserByEmail(GetUserByEmailRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            User foundUser = userService.getByEmail(request.getEmail());

            responseObserver.onNext(responseMapper.toDto(foundUser));
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getReason());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void isFieldTaken(IsFieldTakenRequest request, StreamObserver<IsFieldTakenResponse> responseObserver) {
        try {
            boolean isTaken = userService.checkTaken(request.getField(), request.getValue());

            IsFieldTakenResponse response = IsFieldTakenResponse.newBuilder().setIsTaken(isTaken).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getReason());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getUserByUsername(GetUserByUsernameRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            User foundUser = userService.getByUsername(request.getUsername());

            responseObserver.onNext(responseMapper.toDto(foundUser));
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getReason());
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

    @Override
    public void getUsersByIds(GetUsersByIdsRequest request, StreamObserver<UsersResponse> responseObserver) {
        try {
            List<UUID> ids = request.getIdsList().stream()
                    .map(UUID::fromString)
                    .toList();

            List<User> foundUsers = userService.getByIds(ids);

            List<UserResponse> userResponses = foundUsers.stream()
                    .map(responseMapper::toDto)
                    .toList();

            UsersResponse response = UsersResponse.newBuilder()
                    .addAllUsers(userResponses)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ResponseStatusException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getReason());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
