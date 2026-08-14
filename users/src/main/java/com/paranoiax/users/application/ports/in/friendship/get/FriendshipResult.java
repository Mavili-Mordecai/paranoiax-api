package com.paranoiax.users.application.ports.in.friendship.get;

import java.util.List;

public record FriendshipResult(
        List<FriendshipDetails> data,
        boolean hasMore,
        long serverTimeInMillis
) {
}