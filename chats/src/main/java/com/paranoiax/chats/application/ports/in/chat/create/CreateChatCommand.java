package com.paranoiax.chats.application.ports.in.chat.create;

import java.util.List;

public record CreateChatCommand(
        String name,
        String type,
        List<ParticipantDetails> participants
) {
}
