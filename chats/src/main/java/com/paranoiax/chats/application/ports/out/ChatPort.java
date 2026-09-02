package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;

import java.util.List;

public interface ChatPort {
    Chat insert(Chat chat);
    Chat update(Chat chat);
    List<Chat> findAll();
    void delete(ChatId chatId);
}
