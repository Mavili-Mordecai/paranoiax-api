package com.paranoiax.chats.application.ports.in.invite.use;

import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDeviceDetails;
import com.paranoiax.core.application.OperationCommand;

import java.util.List;
import java.util.UUID;

public record UseInviteCommand(
        UUID userId,
        UUID inviteId,
        List<ParticipantDeviceDetails> deviceKeys,
        String operationId
) implements OperationCommand {
}
