package com.paranoiax.chats.application.ports.in.chat.create;

import java.util.UUID;

public record ParticipantDeviceDetails(
        UUID id,
        String encryptionKey
) {
}
