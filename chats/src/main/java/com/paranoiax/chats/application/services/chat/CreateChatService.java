package com.paranoiax.chats.application.services.chat;

import com.paranoiax.chats.application.ports.in.chat.create.CreateChatCommand;
import com.paranoiax.chats.application.ports.in.chat.create.CreateChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.create.ParticipantDetails;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chat.ChatType;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.domain.models.participant.ParticipantPermission;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateChatService implements CreateChatUseCase {
    private final OperationExecutor executor;
    private final ChatPort chatPort;
    private final ParticipantPort participantPort;
    private final ParticipantKeyPort participantKeyPort;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public CreateChatService(
            OperationExecutor executor,
            ChatPort chatPort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.executor = executor;
        this.chatPort = chatPort;
        this.participantPort = participantPort;
        this.participantKeyPort = participantKeyPort;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public ChatId execute(CreateChatCommand command) {
        return executor.execute(command, Chat.class, lockTtl, resultTtl, () -> {
            Chat chat = Chat.create(ChatType.valueOf(command.type()));
            Map<UUID, Participant> participants = command.participants()
                    .stream()
                    .map(details -> Participant.create(
                            chat.getId(),
                            new UserId(details.userId()),
                            command.userId().equals(details.userId()) ? ParticipantRole.OWNER : ParticipantRole.MEMBER,
                            command.userId().equals(details.userId()) ? Set.of() : getDefaultPermissions(chat.getType())
                    ))
                    .collect(Collectors.toMap(it -> it.getUserId().value(), Function.identity()));

            List<ParticipantKey> keys = command.participants()
                    .stream()
                    .flatMap(details -> {
                        Participant participant = participants.get(details.userId());
                        return details.devices()
                                .stream()
                                .map(device -> ParticipantKey.create(
                                        participant.getId(),
                                        new DeviceId(device.id()),
                                        new EncryptionKey(device.encryptionKey())
                                ));
                    })
                    .collect(Collectors.toList());

            chatPort.insert(chat);
            participantPort.insertAll(participants.values());
            participantKeyPort.insertAll(keys);

            return chat;
        }).getId();
    }

    public static Set<ParticipantPermission> getDefaultPermissions(ChatType type) {
        return switch (type) {
            case SAVED, PRIVATE, ISOLATED -> Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE);
            case GROUP -> Set.of(ParticipantPermission.ADD, ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE);
            case CHANNEL -> Set.of();
        };
    }
}
