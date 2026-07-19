package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AuthRequest(
        @NotBlank(message = "FIELD_REQUIRED") String signature
) {
}