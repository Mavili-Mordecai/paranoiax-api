package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChallengeResponse(
        String challenge,
        Long expiresIn
) {
}