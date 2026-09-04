package com.paranoiax.chats.infrastructure.adapters.persistence.chat;

import com.paranoiax.chats.infrastructure.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaChatRepository extends JpaRepository<ChatEntity, UUID> {

    @Query("SELECT DISTINCT c FROM ChatEntity c JOIN FETCH c.participants p WHERE p.userId = :userId")
    List<ChatEntity> findByUserId(@Param("userId") UUID userId);
}
