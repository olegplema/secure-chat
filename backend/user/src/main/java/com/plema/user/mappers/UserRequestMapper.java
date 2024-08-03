package com.plema.user.mappers;

import org.mapstruct.Mapper;

import com.plema.grpc.user.CreateUserRequest;
import com.plema.grpc.user.CreateUserResponse;
import com.plema.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends Mapable<User, CreateUserRequest> {
}
