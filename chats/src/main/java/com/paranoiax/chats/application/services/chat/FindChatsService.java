package com.paranoiax.chats.application.services.chat;

import com.paranoiax.chats.application.ports.in.chat.findAll.ChatDetails;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsByUserIdQuery;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.GroupProfilePort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileId;
import com.paranoiax.core.domain.users.UserId;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindChatsService implements FindChatsUseCase {
    private final ChatPort chatPort;
    private final GroupProfilePort groupProfilePort;

    public FindChatsService(ChatPort chatPort, GroupProfilePort groupProfilePort) {
        this.chatPort = chatPort;
        this.groupProfilePort = groupProfilePort;
    }

    @Override
    public List<ChatDetails> execute(FindChatsByUserIdQuery query) {
        List<Chat> chats = chatPort.findAll(new UserId(query.userId()));

        Set<GroupProfileId> multipartyChatIds = chats.stream()
                .filter(Chat::isMultiparty)
                .map(chat -> new GroupProfileId(chat.getId().value()))
                .collect(Collectors.toSet());

        Map<UUID, GroupProfile> groups;
        if (multipartyChatIds.isEmpty()) {
            groups = Collections.emptyMap();
        } else {
            groups = groupProfilePort.findAllById(multipartyChatIds)
                    .stream()
                    .collect(Collectors.toMap(group -> group.getId().value(), Function.identity()));
        }

        return chats.stream()
                .map(chat -> ChatDetails.of(
                        chat,
                        chat.isMultiparty() ? groups.get(chat.getId().value()) : null
                ))
                .collect(Collectors.toList());
    }
}
