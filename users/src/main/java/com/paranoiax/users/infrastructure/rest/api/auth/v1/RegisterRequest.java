package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegisterRequest(
        @NotBlank(message = "FIELD_REQUIRED") String username,
        @NotBlank(message = "FIELD_REQUIRED") String registrationToken,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotNull(message = "FIELD_REQUIRED") @Valid DeviceRequest device
) {
}