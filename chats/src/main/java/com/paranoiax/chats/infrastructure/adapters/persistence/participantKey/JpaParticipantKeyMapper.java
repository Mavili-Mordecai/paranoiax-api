package com.paranoiax.chats.infrastructure.adapters.persistence.participantKey;

import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKeyId;
import com.paranoiax.chats.infrastructure.entities.ParticipantKeyEntity;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.KeyVersion;
import com.paranoiax.core.domain.devices.DeviceId;
import org.springframework.stereotype.Component;

@Component
public class JpaParticipantKeyMapper {
    public ParticipantKey toDomain(ParticipantKeyEntity entity) {
        return ParticipantKey.of(
                new ParticipantKeyId(entity.getId()),
                new ParticipantId(entity.getParticipantId()),
                new DeviceId(entity.getDeviceId()),
                new KeyVersion(entity.getKeyVersion()),
                new EncryptionKey(entity.getEncryptionKey())
        );
    }

    public ParticipantKeyEntity toEntity(ParticipantKey domain) {
        return ParticipantKeyEntity.builder()
                .id(domain.getId().value())
                .participantId(domain.getParticipantId().value())
                .deviceId(domain.getDeviceId().value())
                .keyVersion(domain.getKeyVersion().value())
                .encryptionKey(domain.getEncryptionKey().value())
                .build();
    }
}
