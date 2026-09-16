package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UseInviteRequest(
        @NotNull(message = "FIELD_REQUIRED")
        @Size(min = 1, max = 8, message = "INVALID_LENGTH")
        @Valid List<ParticipantDeviceDetailsRequest> deviceKeys
) {
}
