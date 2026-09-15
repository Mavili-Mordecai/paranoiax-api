package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteId;

import java.util.List;
import java.util.Optional;

public interface InvitePort {
    Optional<Invite> findById(InviteId id);
    List<Invite> findAll(ChatId chatId);
    Invite insert(Invite invite);
    Invite update(Invite invite);
    void deleteById(InviteId id);
}
