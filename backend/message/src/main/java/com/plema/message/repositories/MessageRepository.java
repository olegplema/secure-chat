package com.plema.message.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plema.message.entities.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sendById = :userId AND m.sendToId = :chatWithId) OR " +
            "(m.sendById = :chatWithId AND m.sendToId = :userId) " +
            "ORDER BY m.createdAt DESC")
    List<Message> findChatMessages(@Param("userId") UUID userId,
            @Param("chatWithId") UUID chatWithId,
            Pageable pageable);

    @Query(value = """
                SELECT DISTINCT send_to_id as user_id
                FROM (SELECT * FROM message ORDER BY updated_at DESC) as sorted
                WHERE send_by_id = :userId
                UNION
                SELECT DISTINCT send_by_id
                FROM (SELECT * FROM message ORDER BY updated_at DESC) as sorted
                WHERE send_to_id = :userId
                LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<UUID> findDistinctChatPartners(
            @Param("userId") UUID userId,
            @Param("limit") int limit,
            @Param("offset") int offset);
}
