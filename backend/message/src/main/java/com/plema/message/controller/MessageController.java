package com.plema.message.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import com.plema.message.dtos.CreateMessageDto;
import com.plema.message.dtos.FullUserInfo;
import com.plema.message.dtos.MessageDto;
import com.plema.message.entities.Message;
import com.plema.message.mappers.CreateMessageMapper;
import com.plema.message.mappers.MessageMapper;
import com.plema.message.service.interfaces.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CreateMessageMapper createMessageMapper;

    @GetMapping("/chat")
    public HttpEntity<List<MessageDto>> getChat(
            @RequestHeader(value = "X-User-ID", required = true) UUID userId,
            @RequestParam(required = true) UUID chatWithId,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset) {

        List<Message> messages = messageService.getChat(userId, chatWithId, limit, offset);

        List<MessageDto> response = messageMapper.toDto(messages);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/chats")
    public HttpEntity<List<FullUserInfo>> getChats(@RequestHeader(value = "X-User-ID", required = true) UUID userId,
            @RequestParam(required = true) UUID chatWithId,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset) {
        List<FullUserInfo> response = messageService.getChats(userId, limit, offset);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/send")
    public HttpEntity<MessageDto> sendMessage(@RequestBody @Valid CreateMessageDto createMessageDto) {
        Message message = createMessageMapper.toEntity(createMessageDto);
        Message createdMessage = messageService.sendMessage(message);

        MessageDto response = messageMapper.toDto(createdMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
