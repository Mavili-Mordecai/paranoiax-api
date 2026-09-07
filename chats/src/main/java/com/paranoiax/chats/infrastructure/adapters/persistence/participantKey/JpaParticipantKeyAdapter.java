package com.paranoiax.chats.infrastructure.adapters.persistence.participantKey;

import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.core.domain.devices.DeviceId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaParticipantKeyAdapter implements ParticipantKeyPort {
    private final JpaParticipantKeyMapper mapper;
    private final JpaParticipantKeyRepository repository;

    @Override
    public List<ParticipantKey> insertAll(List<ParticipantKey> participantKeys) {
        return repository.saveAll(participantKeys.stream()
                        .map(mapper::toEntity)
                        .toList()
                )
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<ParticipantKey> findAll(DeviceId deviceId) {
        return repository.findAllByDeviceId(deviceId.value())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteAll(DeviceId deviceId) {
        repository.deleteAllByDeviceId(deviceId.value());
    }
}
