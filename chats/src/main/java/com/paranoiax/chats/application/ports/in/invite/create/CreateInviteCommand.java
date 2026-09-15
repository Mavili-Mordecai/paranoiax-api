package com.paranoiax.chats.application.ports.in.invite.create;

import com.paranoiax.core.application.OperationCommand;

import java.time.Instant;
import java.util.UUID;

public record CreateInviteCommand(
        UUID userId,
        UUID chatId,
        Integer maxUses,
        Instant expiresAt,
        String operationId
) implements OperationCommand {
}
