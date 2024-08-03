package com.plema.user.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.plema.grpc.user.CreateUserResponse;
import com.plema.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends Mapable<User, CreateUserResponse> {

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    CreateUserResponse toDto(User user);

    @Named("uuidToString")
    default String mapUUIDToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

}
