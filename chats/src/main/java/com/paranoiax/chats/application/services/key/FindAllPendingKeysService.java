package com.paranoiax.chats.application.services.key;

import com.paranoiax.chats.application.ports.in.key.findAll.FindAllPendingKeysQuery;
import com.paranoiax.chats.application.ports.in.key.findAll.FindAllPendingKeysUseCase;
import com.paranoiax.chats.application.ports.in.key.findAll.PendingKeyDetails;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.domain.devices.DeviceId;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FindAllPendingKeysService implements FindAllPendingKeysUseCase {
    private final ParticipantKeyPort participantKeyPort;
    private final ParticipantPort participantPort;

    public FindAllPendingKeysService(ParticipantKeyPort participantKeyPort, ParticipantPort participantPort) {
        this.participantKeyPort = participantKeyPort;
        this.participantPort = participantPort;
    }

    @Override
    public List<PendingKeyDetails> execute(FindAllPendingKeysQuery query) {
        List<ParticipantKey> keys = participantKeyPort.findAll(new DeviceId(query.deviceId()));

        if (keys.isEmpty()) {
            return Collections.emptyList();
        }

        Set<ParticipantId> participantIds = keys.stream()
                .map(ParticipantKey::getParticipantId)
                .collect(Collectors.toSet());

        Map<ParticipantId, ChatId> chats = participantPort.findAllById(participantIds)
                .stream()
                .collect(Collectors.toMap(Participant::getId, Participant::getChatId, (existing, _) -> existing));

        return keys.stream()
                .filter(key -> chats.containsKey(key.getParticipantId()))
                .map(key -> PendingKeyDetails.from(key, chats.get(key.getParticipantId())))
                .collect(Collectors.toList());
    }
}
