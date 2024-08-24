package com.plema.message.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(indexes = {
        @Index(name = "idx_send_by_id", columnList = "sendById"),
        @Index(name = "idx_send_to_id", columnList = "sendToId")
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String messageSender;

    private String messageReciever;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "send_by_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sendBy;

    @Column(name = "send_by_id", updatable = false, nullable = false)
    private UUID sendById;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "send_to_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sendTo;

    @Column(name = "send_to_id", updatable = false, nullable = false)
    private UUID sendToId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
