package com.paranoiax.users.domain.models.friendship.key;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record FriendshipKeyId(UUID value) {
    public FriendshipKeyId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static FriendshipKeyId create() {
        return new FriendshipKeyId(UUID.randomUUID());
    }
}
