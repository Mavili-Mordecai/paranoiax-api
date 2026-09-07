package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.application.ports.in.chat.create.CreateChatCommand;
import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDetails;
import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDeviceDetails;
import jakarta.validation.constraints.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateChatRequest(
        @NotNull(message = "FIELD_REQUIRED")
        @NotBlank(message = "FIELD_REQUIRED")
        String name,
        @NotNull(message = "FIELD_REQUIRED")
        @NotBlank(message = "FIELD_REQUIRED")
        String type,
        @NotNull(message = "FIELD_REQUIRED")
        @Size(min = 1, max = 8, message = "INVALID_LENGTH")
        List<ParticipantDetailsRequest> participants
) {
    public CreateChatCommand toCommand(UUID userId, String operationId) {
        return new CreateChatCommand(
                name,
                type,
                userId,
                participants.stream()
                        .map(it -> new ParticipantDetails(
                                it.id(),
                                it.devices()
                                        .stream()
                                        .map(device -> new ParticipantDeviceDetails(device.id(), device.encryptionKey()))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList()),
                operationId
        );
    }
}
