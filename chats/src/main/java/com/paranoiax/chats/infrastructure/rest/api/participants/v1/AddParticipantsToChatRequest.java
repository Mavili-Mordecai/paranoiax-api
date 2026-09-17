package com.paranoiax.chats.infrastructure.rest.api.participants.v1;

import com.paranoiax.chats.application.ports.in.participant.addParticipant.AddParticipantsToChatCommand;
import com.paranoiax.chats.infrastructure.rest.api.chats.v1.ParticipantDetailsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddParticipantsToChatRequest(
        @NotNull(message = "FIELD_REQUIRED")
        @Valid List<ParticipantDetailsRequest> participants
) {
        public AddParticipantsToChatCommand toCommand(UUID userId, UUID chatId, String operationId) {
                return new AddParticipantsToChatCommand(
                        userId,
                        chatId,
                        ParticipantDetailsRequest.toParticipantDetails(participants),
                        operationId
                );
        }
}
