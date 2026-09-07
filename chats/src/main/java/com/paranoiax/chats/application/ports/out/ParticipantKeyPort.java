package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.domain.devices.DeviceId;

import java.util.List;

public interface ParticipantKeyPort {
    List<ParticipantKey> insertAll(List<ParticipantKey> participantKeys);
    List<ParticipantKey> findAll(DeviceId deviceId);
    void deleteAll(DeviceId deviceId);
}
