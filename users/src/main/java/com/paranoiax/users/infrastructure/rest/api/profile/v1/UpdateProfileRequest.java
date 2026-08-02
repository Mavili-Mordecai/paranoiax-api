package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateProfileRequest(
        @Size(min = 5, max = 32, message = "INVALID_LENGTH")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "INVALID_PATTERN")
        String username,
        @Size(min = 1, max = 64, message = "INVALID_LENGTH")
        String firstName,
        @Size(min = 1, max = 64, message = "INVALID_LENGTH")
        String lastName,
        @Size(min = 1, max = 192, message = "INVALID_LENGTH")
        String bio
) {
}