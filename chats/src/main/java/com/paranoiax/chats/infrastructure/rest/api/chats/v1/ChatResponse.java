package com.paranoiax.chats.infrastructure.rest.api.chats.v1;

import com.paranoiax.chats.application.ports.in.chat.findAll.ChatDetails;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
