package com.paranoiax.chats.application.ports.in.participant.addParticipant;

import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDetails;
import com.paranoiax.core.application.OperationCommand;

import java.util.List;
import java.util.UUID;

public record AddParticipantsToChatCommand(
        UUID userId,
        UUID chatId,
        List<ParticipantDetails> participants,
        String operationId
) implements OperationCommand {
}
