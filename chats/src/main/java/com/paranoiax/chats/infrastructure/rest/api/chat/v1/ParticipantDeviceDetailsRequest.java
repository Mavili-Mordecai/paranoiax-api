package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantDeviceDetailsRequest(
        @NotNull(message = "FIELD_REQUIRED")
        UUID id,
        @NotNull(message = "FIELD_REQUIRED")
        @NotBlank(message = "FIELD_REQUIRED")
        String encryptionKey
) {
}
