package com.plema.message.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class MessageDto {
    private UUID id;
    private String messageSender;
    private String messageReceiver;
    private UUID sendById;
    private UUID sendToId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
