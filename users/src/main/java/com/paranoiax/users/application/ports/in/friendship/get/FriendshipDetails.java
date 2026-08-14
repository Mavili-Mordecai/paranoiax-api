package com.paranoiax.users.application.ports.in.friendship.get;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Instant;

public record FriendshipDetails(
        FriendshipId id,
        UserId friendId,
        FriendshipStatus status,
        FriendshipAttributes attributes,
        Instant createdAt
) {
    public static FriendshipDetails from(Friendship friendship) {
        return new FriendshipDetails(
                friendship.getId(),
                friendship.getFriendId(),
                friendship.getStatus(),
                friendship.getAttributes(),
                friendship.getCreatedAt()
        );
    }
}