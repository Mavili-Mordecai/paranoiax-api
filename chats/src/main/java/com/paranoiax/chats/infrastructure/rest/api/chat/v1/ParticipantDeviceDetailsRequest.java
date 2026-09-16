package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDeviceDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantDeviceDetailsRequest(
        @NotNull(message = "FIELD_REQUIRED")
        UUID id,
        @NotBlank(message = "FIELD_REQUIRED")
        String encryptionKey
) {
    public ParticipantDeviceDetails toDetails() {
        return new ParticipantDeviceDetails(id, encryptionKey);
    }
}
