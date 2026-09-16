package com.paranoiax.chats.application.ports.in.chat.create;

import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.devices.DeviceId;

import java.util.UUID;

public record ParticipantDeviceDetails(
        UUID id,
        String encryptionKey
) {
    public ParticipantKey toKey(ParticipantId participantId) {
        return ParticipantKey.create(
                participantId,
                new DeviceId(id),
                new EncryptionKey(encryptionKey)
        );
    }
}
