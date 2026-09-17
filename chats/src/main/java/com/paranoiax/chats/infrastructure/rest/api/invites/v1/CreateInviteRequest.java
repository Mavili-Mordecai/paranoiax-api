package com.paranoiax.chats.infrastructure.rest.api.invites.v1;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateInviteRequest(
        @Positive(message = "NUMBER_MUST_BE_POSITIVE") Integer maxUses,
        @Future(message = "TIMESTAMP_MUST_BE_IN_FUTURE") Instant expiresAt
) {
}
