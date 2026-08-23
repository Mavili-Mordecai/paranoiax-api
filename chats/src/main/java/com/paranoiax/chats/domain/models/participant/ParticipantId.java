package com.paranoiax.chats.domain.models.participant;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record ParticipantId(UUID value) {
    public ParticipantId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static ParticipantId create() {
        return new ParticipantId(UUID.randomUUID());
    }
}
