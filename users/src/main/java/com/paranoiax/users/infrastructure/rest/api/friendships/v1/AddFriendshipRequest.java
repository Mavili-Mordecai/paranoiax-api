package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddFriendshipRequest(
        @NotNull(message = "FIELD_REQUIRED") UUID friendId,
        @Size(max = FriendshipAttributes.MAX_SIZE) String attributes
) {
}