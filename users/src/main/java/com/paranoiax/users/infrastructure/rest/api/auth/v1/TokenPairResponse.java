package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import com.paranoiax.users.application.ports.in.auth.TokenPair;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenPairResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenPairResponse from(TokenPair tokenPair) {
        return new TokenPairResponse(tokenPair.accessToken(), tokenPair.refreshToken());
    }
}