package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FriendshipKeyResponse(
        UUID id,
        UUID friendshipId,
        String sharedKey
) {
    public static FriendshipKeyResponse from(FriendshipKey friendshipKey) {
        return new FriendshipKeyResponse(
                friendshipKey.getId().value(),
                friendshipKey.getFriendshipId().value(),
                friendshipKey.getSharedKey().data()
        );
    }
}