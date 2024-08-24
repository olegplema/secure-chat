package com.plema.message.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class User {

    @Id
    private UUID id;

    @JsonIgnore
    @OneToMany(mappedBy = "sendBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sendMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "sendTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages;
}
