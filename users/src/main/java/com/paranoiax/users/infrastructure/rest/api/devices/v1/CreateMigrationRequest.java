package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateMigrationRequest(
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotBlank(message = "FIELD_REQUIRED") String encryptionKey,
        @NotBlank(message = "FIELD_REQUIRED") String deviceSignature
) {
}