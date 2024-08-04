package com.plema.user.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.plema.grpc.user.UserResponse;
import com.plema.user.entities.User;

@Mapper(componentModel = "spring")
public interface CreateUserResponseMapper extends Mapable<User, UserResponse> {

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    UserResponse toDto(User user);

    @Named("uuidToString")
    default String mapUUIDToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

}
