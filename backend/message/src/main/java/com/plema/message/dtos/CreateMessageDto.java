package com.plema.message.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMessageDto {
    @NotBlank(message = "Message sender cannot be blank")
    private String messageSender;

    @NotBlank(message = "Message receiver cannot be blank")
    private String messageReceiver;

    @NotNull(message = "SendById cannot be null")
    private UUID sendById;

    @NotNull(message = "SendToId cannot be null")
    private UUID sendToId;
}
