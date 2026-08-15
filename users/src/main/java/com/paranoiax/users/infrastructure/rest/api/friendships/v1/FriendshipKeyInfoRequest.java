package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FriendshipKeyInfoRequest(
        @NotNull(message = "FIELD_REQUIRED") UUID deviceId,
        @NotNull(message = "FIELD_REQUIRED") @Size(max = 1200, message = "INVALID_LENGTH") String sharedKey
) {
}