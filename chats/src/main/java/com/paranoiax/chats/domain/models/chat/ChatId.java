package com.paranoiax.chats.domain.models.chat;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record ChatId(UUID value) {
    public ChatId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static ChatId create() {
        return new ChatId(UUID.randomUUID());
    }
}
