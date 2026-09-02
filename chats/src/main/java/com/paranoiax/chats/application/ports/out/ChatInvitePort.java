package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chatInvite.ChatInvite;
import com.paranoiax.chats.domain.models.chatInvite.ChatInviteId;

import java.util.List;
import java.util.Optional;

public interface ChatInvitePort {
    Optional<ChatInvite> findById(ChatInviteId chatId);
    List<ChatInvite> findAll(ChatId chatId);
    ChatInvite insert(ChatInvite chatInvite);
    ChatInvite update(ChatInvite chatInvite);
    void deleteById(ChatInviteId id);
}
