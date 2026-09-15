package com.paranoiax.chats.application.ports.in.invite.create;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteId;
import com.paranoiax.chats.domain.models.invite.InviteMaxUses;
import com.paranoiax.chats.domain.models.invite.InviteUsesCount;

import java.time.Instant;

public record InviteDetails(
        InviteId inviteId,
        ChatId chatId,
        InviteMaxUses maxUses,
        InviteUsesCount usesCount,
        Instant expiresAt
) {
    public static InviteDetails from(Invite invite) {
        return new InviteDetails(
                invite.getId(),
                invite.getChatId(),
                invite.getMaxUses(),
                invite.getUsesCount(),
                invite.getExpiresAt()
        );
    }
}
