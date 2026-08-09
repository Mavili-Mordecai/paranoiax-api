package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record DeleteFriendshipKeysRequest(
        @NotNull(message = "FIELD_REQUIRED") @Size(max = 1000, message = "INVALID_LENGTH") List<UUID> ids
) {
}