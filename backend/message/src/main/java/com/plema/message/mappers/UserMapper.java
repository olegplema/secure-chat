package com.plema.message.mappers;

import org.mapstruct.Mapper;

import com.plema.grpc.user.UserResponse;
import com.plema.message.dtos.FullUserInfo;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mapable<UserResponse, FullUserInfo> {

}
