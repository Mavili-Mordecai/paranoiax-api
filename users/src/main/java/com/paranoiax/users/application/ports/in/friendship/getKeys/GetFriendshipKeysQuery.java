package com.paranoiax.users.application.ports.in.friendship.getKeys;

import java.util.UUID;

public record GetFriendshipKeysQuery(
        UUID deviceId,
        Integer limit,
        Integer offset
) {
}