package com.paranoiax.users.domain.models.friendship.key;

import com.paranoiax.core.domain.Require;

public record FriendshipSharedKey(String data) {
    public static final int MAX_SIZE = 1200;

    public FriendshipSharedKey {
        Require.hasLength(data, "data", 1, MAX_SIZE);
    }
}