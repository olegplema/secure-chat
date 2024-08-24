package com.plema.message.mappers;

import org.mapstruct.Mapper;

import com.plema.message.dtos.MessageDto;
import com.plema.message.entities.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper extends Mapable<Message, MessageDto> {

}
