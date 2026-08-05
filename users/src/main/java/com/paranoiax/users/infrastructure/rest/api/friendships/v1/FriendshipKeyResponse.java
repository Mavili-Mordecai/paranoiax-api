package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FriendshipKeyResponse(
        UUID id,
        UUID friendshipId,
        String sharedKey
) {
}