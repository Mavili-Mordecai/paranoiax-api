package com.paranoiax.chats.application.ports.in.chat.findAll;

import java.util.UUID;

public record FindChatsByUserIdQuery(
        UUID userId
) {
}
