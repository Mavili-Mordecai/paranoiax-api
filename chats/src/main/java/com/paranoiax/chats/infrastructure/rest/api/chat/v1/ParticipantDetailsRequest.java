package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDetails;
import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDeviceDetails;
import jakarta.validation.constraints.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ParticipantDetailsRequest(
        @NotNull(message = "FIELD_REQUIRED")
        UUID id,
        @NotNull(message = "FIELD_REQUIRED")
        @Size(min = 1, max = 8, message = "INVALID_LENGTH")
        List<ParticipantDeviceDetailsRequest> devices
) {
    public static List<ParticipantDetails> toParticipantDetails(List<ParticipantDetailsRequest> participants) {
        return participants.stream()
                .map(it -> new ParticipantDetails(
                        it.id(),
                        it.devices()
                                .stream()
                                .map(device -> new ParticipantDeviceDetails(device.id(), device.encryptionKey()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
