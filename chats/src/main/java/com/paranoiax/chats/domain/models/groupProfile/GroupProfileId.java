package com.paranoiax.chats.domain.models.groupProfile;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record GroupProfileId(UUID value) {
    public GroupProfileId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static GroupProfileId from(ChatId chatId) {
        return new GroupProfileId(chatId.value());
    }
}
