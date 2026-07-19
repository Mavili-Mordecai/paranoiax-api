package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record InviteResponse(
        String host,
        String registrationToken,
        String spkiPin
) {
    public static InviteResponse of(String host, String registrationToken, String spkiPin) {
        return new InviteResponse(
                host,
                registrationToken,
                spkiPin
        );
    }
}