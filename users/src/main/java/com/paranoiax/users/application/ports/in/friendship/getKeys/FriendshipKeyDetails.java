package com.paranoiax.users.application.ports.in.friendship.getKeys;

import java.util.UUID;

public record FriendshipKeyDetails(
        UUID id,
        UUID friendshipId,
        String sharedKey
) {
}