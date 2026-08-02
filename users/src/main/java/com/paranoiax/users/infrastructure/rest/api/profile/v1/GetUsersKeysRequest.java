package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.Set;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetUsersKeysRequest(
        @NotNull @Size(min = 1) Set<UUID> userIds
) {
}