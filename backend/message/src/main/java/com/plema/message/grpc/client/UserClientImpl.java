package com.plema.message.grpc.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.cloud.location.GetLocationRequestOrBuilder;
import com.plema.grpc.user.GetUserByIdRequest;
import com.plema.grpc.user.GetUsersByIdsRequest;
import com.plema.grpc.user.UsersResponse;
import com.plema.grpc.user.UserResponse;
import com.plema.grpc.user.UserServiceGrpc.UserServiceBlockingStub;
import com.plema.message.dtos.FullUserInfo;
import com.plema.message.grpc.client.interfaces.UserClient;
import com.plema.message.mappers.GrpcStatusMapper;
import com.plema.message.mappers.UserMapper;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class UserClientImpl implements UserClient {

    @GrpcClient("UserService")
    private UserServiceBlockingStub blockingStub;

    @Autowired
    private GrpcStatusMapper statusMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public FullUserInfo getUserById(UUID id) {
        GetUserByIdRequest request = GetUserByIdRequest.newBuilder().setId(id.toString()).build();
        try {
            UserResponse user = blockingStub.getUserById(request);
            return userMapper.toDto(user);
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

    @Override
    public List<FullUserInfo> getUsersByIds(List<UUID> ids) {
        GetUsersByIdsRequest request = GetUsersByIdsRequest.newBuilder().addAllIds(ids.stream()
                .map(UUID::toString)
                .collect(Collectors.toList())).build();
        try {

            List<UserResponse> users = blockingStub.getUsersByIds(request).getUsersList();
            return userMapper.toDto(users);
        } catch (StatusRuntimeException e) {
            HttpStatus status = statusMapper.map(e.getStatus().getCode());
            throw new ResponseStatusException(status, e.getStatus().getDescription());
        }
    }

}
