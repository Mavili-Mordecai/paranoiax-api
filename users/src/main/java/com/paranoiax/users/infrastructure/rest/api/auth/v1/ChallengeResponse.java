package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import com.paranoiax.users.domain.models.challenge.Challenge;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Duration;
import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChallengeResponse(
        String challenge,
        Long expiresIn
) {
    public static ChallengeResponse from(Challenge challenge) {
        Instant now = Instant.now();
        return new ChallengeResponse(
                challenge.getChallenge().value(),
                Duration.between(now, challenge.getExpiresAt()).toMillis()
        );
    }
}