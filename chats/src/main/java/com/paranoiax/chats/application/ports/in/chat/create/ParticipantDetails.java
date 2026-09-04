package com.paranoiax.chats.application.ports.in.chat.create;

import java.util.List;

public record ParticipantDetails(
        String id,
        List<ParticipantDeviceDetails> device
) {
}
