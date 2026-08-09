package com.paranoiax.users.application.ports.in.friendship.add;

import java.util.UUID;

public record FriendshipKeyInfo(
        UUID deviceId,
        String sharedKey
) {
}
