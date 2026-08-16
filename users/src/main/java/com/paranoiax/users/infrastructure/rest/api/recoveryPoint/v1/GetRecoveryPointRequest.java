package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetRecoveryPointRequest(
        @NotBlank(message = "FIELD_REQUIRED")
        String signature
) {
}