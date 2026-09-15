package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateInviteRequest(
        Integer maxUses,
        Instant expiresAt
) {
}
