package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.users.UserId;

import java.util.List;
import java.util.Optional;

public interface ChatPort {
    Chat insert(Chat chat);
    Chat update(Chat chat);
    List<Chat> findAll(UserId userId);
    Optional<Chat> findById(ChatId chatId);
    void delete(ChatId chatId);
}
