package com.paranoiax.chats.domain.models.chatInvite;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record ChatInviteId(UUID value) {
    public ChatInviteId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static ChatInviteId create() {
        return new ChatInviteId(UUID.randomUUID());
    }
}
