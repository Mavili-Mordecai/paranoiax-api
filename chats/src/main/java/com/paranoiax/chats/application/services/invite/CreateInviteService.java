package com.paranoiax.chats.application.services.invite;

import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteUseCase;
import com.paranoiax.chats.application.ports.in.invite.create.InviteDetails;
import com.paranoiax.chats.application.ports.out.InvitePort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteMaxUses;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantPermission;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core.domain.exceptions.AccessDeniedException;
import com.paranoiax.core.domain.users.UserId;

import java.time.Duration;

public class CreateInviteService implements CreateInviteUseCase {
    private final InvitePort invitePort;
    private final ParticipantPort participantPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public CreateInviteService(
            InvitePort invitePort,
            ParticipantPort participantPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.invitePort = invitePort;
        this.participantPort = participantPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public InviteDetails execute(CreateInviteCommand command) {
        return InviteDetails.from(executor.execute(command, Invite.class, lockTtl, resultTtl, () -> {
            Participant participant = participantPort.findBy(new ChatId(command.chatId()), new UserId(command.userId()))
                    .orElseThrow(AccessDeniedException::new);

            if (!participant.hasPermission(ParticipantPermission.CREATE_INVITE)) {
                throw new AccessDeniedException();
            }

            Invite invite = Invite.create(
                    participant.getChatId(),
                    participant.getUserId(),
                    command.maxUses() == null ? null : new InviteMaxUses(command.maxUses()),
                    command.expiresAt()
            );

            invitePort.insert(invite);

            return invite;
        }));
    }
}
