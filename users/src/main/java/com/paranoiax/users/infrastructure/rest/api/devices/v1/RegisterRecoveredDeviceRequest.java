package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import com.paranoiax.users.application.ports.in.devices.recover.RegisterRecoveredDeviceCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegisterRecoveredDeviceRequest(
        @NotBlank(message = "FIELD_REQUIRED") String challenge,
        @NotBlank(message = "FIELD_REQUIRED") String signature,
        @NotBlank(message = "FIELD_REQUIRED") String username,
        @NotBlank(message = "FIELD_REQUIRED") String deviceName,
        @NotNull(message = "FIELD_REQUIRED")
        @Pattern(regexp = "^MOBILE|DESKTOP$", message = "INVALID_DEVICE_TYPE") String deviceType,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey,
        @NotBlank(message = "FIELD_REQUIRED") String encryptionKey,
        @NotBlank(message = "FIELD_REQUIRED") String deviceSignature
) {
    public RegisterRecoveredDeviceCommand toCommand(UUID deviceId, String operationId) {
        return new RegisterRecoveredDeviceCommand(
                deviceId,
                challenge,
                signature,
                username,
                deviceName,
                deviceType,
                identityKey,
                encryptionKey,
                deviceSignature,
                operationId
        );
    }
}