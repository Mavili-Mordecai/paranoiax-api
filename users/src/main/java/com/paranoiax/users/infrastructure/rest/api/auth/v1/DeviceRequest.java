package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeviceRequest(
        @NotNull(message = "FIELD_REQUIRED") UUID id,
        @NotBlank(message = "FIELD_REQUIRED") String name,
        @NotNull(message = "FIELD_REQUIRED")
        @Pattern(regexp = "^MOBILE|DESKTOP$", message = "INVALID_DEVICE_TYPE") String type,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotBlank(message = "FIELD_REQUIRED") String encryptionKey,
        @NotBlank(message = "FIELD_REQUIRED") String deviceSignature
) {
}