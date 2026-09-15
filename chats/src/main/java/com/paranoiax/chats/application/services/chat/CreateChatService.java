package com.paranoiax.chats.application.services.chat;

import com.paranoiax.chats.application.ports.in.chat.create.CreateChatCommand;
import com.paranoiax.chats.application.ports.in.chat.create.CreateChatUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.GroupProfilePort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chat.ChatType;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileName;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core.domain.users.UserId;
import org.jspecify.annotations.NonNull;

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
    private final GroupProfilePort groupProfilePort;
    private final ParticipantPort participantPort;
    private final ParticipantKeyPort participantKeyPort;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public CreateChatService(
            OperationExecutor executor,
            ChatPort chatPort,
            GroupProfilePort groupProfilePort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.executor = executor;
        this.chatPort = chatPort;
        this.groupProfilePort = groupProfilePort;
        this.participantPort = participantPort;
        this.participantKeyPort = participantKeyPort;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    // TODO: Add a check for the existence of users and their devices in the user service
    // TODO: Add sending an event to Kafka
    @Override
    public ChatId execute(CreateChatCommand command) {
        return executor.execute(command, Chat.class, lockTtl, resultTtl, () -> {
            Chat chat = Chat.create(ChatType.valueOf(command.type()));

            Map<UUID, Participant> participants = getParticipants(command, chat);
            List<ParticipantKey> keys = command.participants().stream()
                    .flatMap(details -> details.toKeys(participants.get(details.userId())).stream())
                    .collect(Collectors.toList());

            chatPort.insert(chat);
            participantPort.insertAll(participants.values());
            participantKeyPort.insertAll(keys);

            if (chat.isMultiparty()) {
                groupProfilePort.insert(GroupProfile.create(
                        chat.getId(),
                        command.name() != null ? new GroupProfileName(command.name()) : null,
                        null
                ));
            }

            return chat;
        }).getId();
    }

    private static @NonNull Map<UUID, Participant> getParticipants(CreateChatCommand command, Chat chat) {
        return command.participants()
                .stream()
                .map(participant -> {
                    boolean isSameUser = command.userId().equals(participant.userId());
                    ParticipantRole role = chat.isMultiparty() && isSameUser
                            ? ParticipantRole.OWNER
                            : ParticipantRole.MEMBER;
                    return Participant.create(
                            chat.getId(),
                            new UserId(participant.userId()),
                            role,
                            isSameUser ? Set.of() : chat.getType().getDefaultPermissions(role)
                    );
                })
                .collect(Collectors.toMap(it -> it.getUserId().value(), Function.identity()));
    }
}
