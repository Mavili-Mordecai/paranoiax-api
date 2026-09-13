package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.application.ports.in.chat.getAll.ChatDetails;

import java.time.Instant;

public record ChatResponse(
        String chatId,
        String type,
        String name,
        Integer eventsSeq,
        Instant lastActivityAt,
        Instant createdAt
) {
    public static ChatResponse from(ChatDetails chat) {
        return new ChatResponse(
                chat.id().value().toString(),
                chat.type().name(),
                chat.name() != null ? chat.name().value() : null,
                chat.eventsSeq().value(),
                chat.lastActivityAt(),
                chat.createdAt()
        );
    }
}
