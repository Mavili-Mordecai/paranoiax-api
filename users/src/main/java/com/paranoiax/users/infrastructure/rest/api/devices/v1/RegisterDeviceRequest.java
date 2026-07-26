package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegisterDeviceRequest(
        @NotBlank(message = "FIELD_REQUIRED") String linkToken,
        @NotBlank(message = "FIELD_REQUIRED") String signature,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotBlank(message = "FIELD_REQUIRED") String encryptionKey,
        @NotBlank(message = "FIELD_REQUIRED") String deviceName,
        @Pattern(regexp = "^MOBILE|DESKTOP$", message = "INVALID_DEVICE_TYPE") String deviceType,
        @NotBlank(message = "FIELD_REQUIRED") String deviceSignature
) {
}