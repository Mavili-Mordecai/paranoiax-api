package com.paranoiax.chats.application.ports.in.chat.getAll;

import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chat.ChatType;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileName;
import com.paranoiax.core.domain.EventsSeq;

import java.time.Instant;

public record ChatDetails(
        ChatId id,
        ChatType type,
        GroupProfileName name,
        EventsSeq eventsSeq,
        Instant lastActivityAt,
        Instant createdAt
) {
    public static ChatDetails of(Chat chat, GroupProfile groupProfile) {
        return new ChatDetails(
                chat.getId(),
                chat.getType(),
                groupProfile != null ? groupProfile.getName() : null,
                chat.getEventsSeq(),
                chat.getLastActivityAt(),
                chat.getCreatedAt()
        );
    }
}
