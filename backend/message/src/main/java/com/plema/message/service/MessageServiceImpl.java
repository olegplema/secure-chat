package com.plema.message.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.plema.message.dtos.FullUserInfo;
import com.plema.message.entities.Message;
import com.plema.message.repositories.MessageRepository;
import com.plema.message.service.interfaces.MessageService;
import com.plema.message.service.interfaces.UserService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Message> getChat(UUID userId, UUID chatWithId, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());
        return messageRepository.findChatMessages(userId, chatWithId, pageable);
    }

    @Override
    public List<FullUserInfo> getChats(UUID userId, int limit, int offset) {
        List<UUID> usersIds = messageRepository.findDistinctChatPartners(userId, limit, offset);

        return userService.getUsersByIds(usersIds);
    }

    @Override
    public Message sendMessage(Message message) {
        userService.createIfNotExists(message.getSendById());
        userService.createIfNotExists(message.getSendToId());

        return messageRepository.save(message);
    }
}
