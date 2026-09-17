package com.paranoiax.chats.application.services.participant;

import com.paranoiax.chats.application.ports.in.participant.addParticipant.AddParticipantsToChatCommand;
import com.paranoiax.chats.application.ports.in.participant.addParticipant.AddParticipantsToChatUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chat.ChatType;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantPermission;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core.domain.exceptions.AccessDeniedException;
import com.paranoiax.core.domain.exceptions.InvalidChatTypeException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.core.domain.users.UserId;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddParticipantsToChatService implements AddParticipantsToChatUseCase {
    private final ChatPort chatPort;
    private final ParticipantPort participantPort;
    private final ParticipantKeyPort participantKeyPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public AddParticipantsToChatService(
            ChatPort chatPort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
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
    public void execute(AddParticipantsToChatCommand command) {
        executor.execute(command, Chat.class, lockTtl, resultTtl, () -> {
            Chat chat = chatPort.findById(new ChatId(command.chatId()))
                    .orElseThrow(() -> new NotFoundException("Chat"));

            if (!chat.isMultiparty()) {
                throw new InvalidChatTypeException(
                        chat.getType().name(),
                        ChatType.GROUP.name() + " or " + ChatType.CHANNEL.name()
                );
            }

            Participant participant = participantPort.findBy(chat.getId(), new UserId(command.userId()))
                    .orElseThrow(() -> new NotFoundException("Participant"));

            if (!participant.hasPermission(ParticipantPermission.ADD)) {
                throw new AccessDeniedException();
            }

            List<Participant> newParticipants = command.participants()
                    .stream()
                    .map(participantDetails -> Participant.create(
                            chat.getId(),
                            new UserId(participantDetails.userId()),
                            ParticipantRole.MEMBER,
                            chat.getType().getDefaultPermissions(ParticipantRole.MEMBER)
                    ))
                    .collect(Collectors.toList());

            Map<UUID, Participant> participantsMap = newParticipants.stream()
                    .collect(Collectors.toMap(it -> it.getId().value(), Function.identity()));

            List<ParticipantKey> keys = command.participants().stream()
                    .flatMap(details -> details.toKeys(participantsMap.get(details.userId())).stream())
                    .collect(Collectors.toList());

            chat.advanceEventsSeq();

            chatPort.update(chat);
            participantPort.insertAll(newParticipants);
            participantKeyPort.insertAll(keys);

            return chat;
        });
    }
}
