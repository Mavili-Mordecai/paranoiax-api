package com.paranoiax.chats.application.ports.in.chat.create;

import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ParticipantDetails(
        UUID userId,
        List<ParticipantDeviceDetails> devices
) {
    public List<ParticipantKey> toKeys(Participant participant) {
        return devices.stream()
                .map(device -> device.toKey(participant.getId()))
                .collect(Collectors.toList());
    }
}
