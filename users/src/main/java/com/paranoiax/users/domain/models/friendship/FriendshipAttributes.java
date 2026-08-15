package com.paranoiax.users.domain.models.friendship;

import com.paranoiax.core.domain.Require;

public record FriendshipAttributes(String data) {
    public static final int MAX_SIZE = 5_000;

    public FriendshipAttributes {
        Require.hasLengthIfPresent(data, "data", 1, MAX_SIZE);
    }
}