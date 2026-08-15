package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paranoiax.users.application.ports.in.friendship.get.FriendshipDetails;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Instant;
import java.util.UUID;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FriendshipResponse(
        UUID id,
        UUID friendId,
        FriendshipStatus status,
        String attributes,
        Instant createdAt
) {
    public static FriendshipResponse from(FriendshipDetails friendshipDetails) {
        return new FriendshipResponse(
                friendshipDetails.id().value(),
                friendshipDetails.friendId().value(),
                friendshipDetails.status(),
                friendshipDetails.attributes() != null ? friendshipDetails.attributes().data() : null,
                friendshipDetails.createdAt()
        );
    }
}