package com.paranoiax.chats.application.services.invite;

import com.paranoiax.chats.application.ports.in.invite.use.UseInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.InvitePort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatType;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core.domain.exceptions.AlreadyExistsException;
import com.paranoiax.core.domain.exceptions.InvalidChatTypeException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.core.domain.users.UserId;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UseInviteService implements UseInviteUseCase {
    private final InvitePort invitePort;
    private final ChatPort chatPort;
    private final ParticipantPort participantPort;
    private final ParticipantKeyPort participantKeyPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public UseInviteService(
            InvitePort invitePort,
            ChatPort chatPort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.invitePort = invitePort;
        this.chatPort = chatPort;
        this.participantPort = participantPort;
        this.participantKeyPort = participantKeyPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    // TODO: Add a check for the existence of users and their devices in the user service
    // TODO: Add sending an event to Kafka
    @Override
    public void execute(UseInviteCommand command) {
        executor.execute(command, Chat.class, lockTtl, resultTtl, () -> {
            Invite invite = invitePort.findById(new InviteId(command.inviteId()))
                    .orElseThrow(() -> new NotFoundException("Invite"));

            Chat chat = chatPort.findById(invite.getChatId())
                    .orElseThrow(() -> new NotFoundException("Chat"));

            UserId userId = new UserId(command.userId());

            if (!chat.isMultiparty()) {
                throw new InvalidChatTypeException(
                        chat.getType().name(),
                        ChatType.GROUP.name() + " or " + ChatType.CHANNEL.name()
                );
            }

            if (participantPort.findBy(chat.getId(), userId).isPresent()) {
                throw new AlreadyExistsException("Participant");
            }

            invite.use(Instant.now());
            chat.advanceEventsSeq();

            Participant participant = Participant.create(
                    invite.getChatId(),
                    new UserId(command.userId()),
                    ParticipantRole.MEMBER,
                    chat.getType().getDefaultPermissions(ParticipantRole.MEMBER)
            );

            List<ParticipantKey> keys = command.deviceKeys().stream()
                    .map(it -> it.toKey(participant.getId()))
                    .collect(Collectors.toList());

            invitePort.update(invite);
            chatPort.update(chat);
            participantPort.insert(participant);
            participantKeyPort.insertAll(keys);

            return chat;
        });
    }
}
