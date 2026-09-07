package com.paranoiax.chats.application.ports.in.chat.create;

import com.paranoiax.core.application.OperationCommand;

import java.util.List;
import java.util.UUID;

public record CreateChatCommand(
        String name,
        String type,
        UUID userId,
        List<ParticipantDetails> participants,
        String operationId
) implements OperationCommand {
}
