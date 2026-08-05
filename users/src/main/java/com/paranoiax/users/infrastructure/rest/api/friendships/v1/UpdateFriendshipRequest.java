package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateFriendshipRequest(
        @Size(max = FriendshipAttributes.MAX_SIZE) String attributes
) {
}