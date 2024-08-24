package com.plema.message.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.plema.message.dtos.FullUserInfo;
import com.plema.message.entities.Message;

public interface MessageService {

    List<Message> getChat(UUID userId, UUID chatWithId, int limit, int offset);

    List<FullUserInfo> getChats(UUID userId, int limit, int offset);

    Message sendMessage(Message message);
}
