package com.plema.auth.mappers;

import org.mapstruct.Mapper;

import com.plema.auth.dtos.UserDto;
import com.plema.grpc.user.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mapable<UserResponse, UserDto> {

}
