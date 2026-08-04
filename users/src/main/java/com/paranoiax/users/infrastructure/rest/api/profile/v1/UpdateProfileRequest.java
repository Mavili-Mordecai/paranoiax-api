package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.domain.models.user.Profile;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateProfileRequest(
        @Size(min = 5, max = 32, message = "INVALID_LENGTH")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "INVALID_PATTERN")
        String username,
        @Size(max = Profile.MAX_SIZE, message = "INVALID_LENGTH")
        String profile
) {
}