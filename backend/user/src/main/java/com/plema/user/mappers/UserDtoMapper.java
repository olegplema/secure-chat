package com.plema.user.mappers;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.plema.user.dtos.UserDto;
import com.plema.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends Mapable<User, UserDto> {

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    UserDto toDto(User user);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    List<UserDto> toDto(List<User> users);

    @Named("uuidToString")
    default String mapUUIDToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

}
