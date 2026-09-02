package com.paranoiax.chats.application.ports.in.chat.create;

public record ParticipantDeviceDetails(
        String id,
        String encryptionKey
) {
}
