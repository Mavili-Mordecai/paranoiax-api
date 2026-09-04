package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import java.time.Instant;

public record ChatResponse(
        String chatId,
        String type,
        String name,
        Integer eventsSeq,
        Instant lastActivityAt,
        Instant createdAt
) {

}
