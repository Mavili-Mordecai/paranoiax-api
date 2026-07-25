package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import com.paranoiax.users.application.ports.in.auth.register.DeviceInfo;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserCommand;
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
        @NotNull(message = "FIELD_REQUIRED") @Valid DeviceInfoRequest device
) {
    public RegisterUserCommand toCommand(String operationId) {
        return new RegisterUserCommand(
                username,
                registrationToken,
                identityKey,
                new DeviceInfo(
                        device.id(),
                        device.name(),
                        device.type(),
                        device.identityKey(),
                        device.encryptionKey(),
                        device.deviceSignature()
                ),
                operationId
        );
    }
}