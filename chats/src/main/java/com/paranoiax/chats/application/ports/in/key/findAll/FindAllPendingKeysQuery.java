package com.paranoiax.chats.application.ports.in.key.findAll;

import java.util.UUID;

public record FindAllPendingKeysQuery(
        UUID deviceId
) {
}
