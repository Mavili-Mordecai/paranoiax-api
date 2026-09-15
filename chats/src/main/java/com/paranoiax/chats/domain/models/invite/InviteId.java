package com.paranoiax.chats.domain.models.invite;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record InviteId(UUID value) {
    public InviteId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static InviteId create() {
        return new InviteId(UUID.randomUUID());
    }
}
