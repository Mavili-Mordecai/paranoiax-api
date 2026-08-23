package com.paranoiax.chats.domain.models.participantKey;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record ParticipantKeyId(UUID value) {
    public ParticipantKeyId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static ParticipantKeyId create() {
        return new ParticipantKeyId(UUID.randomUUID());
    }
}
