package com.plema.auth.grpc.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.plema.auth.dtos.UserDto;
import com.plema.auth.grpc.clients.interfaces.UserClient;
import com.plema.auth.mappers.GrpcStatusMapper;
import com.plema.auth.mappers.UserMapper;
import com.plema.grpc.user.CreateUserRequest;
import com.plema.grpc.user.GetUserByEmailRequest;
import com.plema.grpc.user.GetUserByUsernameRequest;
import com.plema.grpc.user.IsFieldTakenRequest;
import com.plema.grpc.user.IsFieldTakenResponse;
import com.plema.grpc.user.UserResponse;
import com.plema.grpc.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class UserClientImpl implements UserClient {

    @GrpcClient("UserService")
    private UserServiceBlockingStub blockingStub;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    protected GrpcStatusMapper statusMapper;

    @Override
    public UserDto getUserByEmail(String email) throws ResponseStatusException {
        GetUserByEmailRequest request = GetUserByEmailRequest.newBuilder().setEmail(email).build();
        try {
            UserResponse user = blockingStub.getUserByEmail(request);
            return userMapper.toDto(user);
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

    @Override
    public UserDto createUser(String username, String email, String pubKey) throws ResponseStatusException {
        CreateUserRequest request = CreateUserRequest.newBuilder().setEmail(email).setUsername(username)
                .setPubKey(pubKey).build();
        try {
            UserResponse user = blockingStub.createUser(request);
            return userMapper.toDto(user);
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

    @Override
    public UserDto getUserByUsername(String username) throws ResponseStatusException {
        GetUserByUsernameRequest request = GetUserByUsernameRequest.newBuilder().setUsername(username).build();
        try {
            UserResponse user = blockingStub.getUserByUsername(request);
            return userMapper.toDto(user);
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

    @Override
    public boolean isFieldTaken(String field, String value) {
        IsFieldTakenRequest request = IsFieldTakenRequest.newBuilder().setField(field).setValue(value).build();
        try {
            IsFieldTakenResponse response = blockingStub.isFieldTaken(request);
            return response.getIsTaken();
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }
}
