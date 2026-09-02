package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantDetails(
        @NotNull(message = "FIELD_REQUIRED")
        @NotBlank(message = "FIELD_REQUIRED")
        String id,
        @NotNull(message = "FIELD_REQUIRED")
        @Min(value = 1, message = "INVALID_LENGTH") @Max(value = 8, message = "INVALID_LENGTH")
        List<ParticipantDeviceDetails> devices
) {
}
