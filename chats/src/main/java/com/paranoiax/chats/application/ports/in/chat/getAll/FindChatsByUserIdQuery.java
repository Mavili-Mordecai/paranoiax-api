package com.paranoiax.chats.application.ports.in.chat.getAll;

import java.util.UUID;

public record FindChatsByUserIdQuery(
        UUID userId
) {
}
