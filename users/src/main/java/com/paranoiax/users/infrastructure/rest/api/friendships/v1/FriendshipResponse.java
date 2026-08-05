package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Instant;
import java.util.UUID;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FriendshipResponse(
        UUID id,
        UUID friendId,
        FriendshipStatus status,
        String attributes,
        Instant createdAt
) {
}