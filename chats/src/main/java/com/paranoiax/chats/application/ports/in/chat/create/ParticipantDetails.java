package com.paranoiax.chats.application.ports.in.chat.create;

import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.devices.DeviceId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ParticipantDetails(
        UUID userId,
        List<ParticipantDeviceDetails> devices
) {
    public List<ParticipantKey> toKeys(Participant participant) {
        return devices.stream()
                .map(device -> ParticipantKey.create(
                        participant.getId(),
                        new DeviceId(device.id()),
                        new EncryptionKey(device.encryptionKey())
                ))
                .collect(Collectors.toList());
    }
}
