package com.plema.message.mappers;

import org.mapstruct.Mapper;

import com.plema.message.dtos.CreateMessageDto;
import com.plema.message.entities.Message;

@Mapper(componentModel = "spring")
public interface CreateMessageMapper extends Mapable<Message, CreateMessageDto> {

}
