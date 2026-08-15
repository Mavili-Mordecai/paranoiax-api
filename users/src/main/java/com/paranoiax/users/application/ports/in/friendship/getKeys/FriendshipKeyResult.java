package com.paranoiax.users.application.ports.in.friendship.getKeys;

import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;

import java.util.List;

public record FriendshipKeyResult(
        List<FriendshipKey> data,
        boolean hasMore,
        long serverTimeInMillis
) {
}