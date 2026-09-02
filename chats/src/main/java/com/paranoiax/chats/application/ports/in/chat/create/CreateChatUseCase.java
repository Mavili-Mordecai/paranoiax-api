package com.paranoiax.chats.application.ports.in.chat.create;

import com.paranoiax.chats.domain.models.chat.ChatId;

public interface CreateChatUseCase {
    ChatId execute(CreateChatCommand command);
}
