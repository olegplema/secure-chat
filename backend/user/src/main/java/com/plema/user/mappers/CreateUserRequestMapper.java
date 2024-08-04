package com.plema.user.mappers;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.plema.grpc.user.CreateUserRequest;
import com.plema.user.entities.User;

@Mapper(componentModel = "spring")
public interface CreateUserRequestMapper extends Mapable<User, CreateUserRequest> {

    @Mapping(target = "id", source = "id", qualifiedByName = "stringToUUID")
    List<User> toEntity(List<CreateUserRequest> userRequests);

    @Named("stringToUUID")
    default UUID mapStringToUUID(String uuidString) {
        return uuidString != null ? UUID.fromString(uuidString) : null;
    }
}
