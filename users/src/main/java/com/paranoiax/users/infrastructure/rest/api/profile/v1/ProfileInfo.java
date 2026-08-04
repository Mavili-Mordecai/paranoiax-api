package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfileInfo(
        String data,
        Integer version
) {
}