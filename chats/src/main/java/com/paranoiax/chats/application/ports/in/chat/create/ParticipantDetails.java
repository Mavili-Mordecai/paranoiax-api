package com.paranoiax.chats.application.ports.in.chat.create;

import java.util.List;
import java.util.UUID;

public record ParticipantDetails(
        UUID userId,
        List<ParticipantDeviceDetails> devices
) {
}
