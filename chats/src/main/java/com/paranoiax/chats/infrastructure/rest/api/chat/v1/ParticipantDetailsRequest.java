package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import jakarta.validation.constraints.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantDetailsRequest(
        @NotNull(message = "FIELD_REQUIRED")
        UUID id,
        @NotNull(message = "FIELD_REQUIRED")
        @Size(min = 1, max = 8, message = "INVALID_LENGTH")
        List<ParticipantDeviceDetailsRequest> devices
) {
}
