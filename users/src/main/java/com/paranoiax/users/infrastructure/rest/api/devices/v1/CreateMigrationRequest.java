package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateMigrationRequest(
        @NotNull(message = "FIELD_REQUIRED") UUID deviceId,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotBlank(message = "FIELD_REQUIRED") String encryptionKey,
        @NotBlank(message = "FIELD_REQUIRED") String deviceSignature
) {
}