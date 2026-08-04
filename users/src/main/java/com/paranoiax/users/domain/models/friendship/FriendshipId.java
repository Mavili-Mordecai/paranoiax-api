package com.paranoiax.users.domain.models.friendship;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record FriendshipId(UUID value) {
    public FriendshipId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }
}