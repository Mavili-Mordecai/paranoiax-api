package com.paranoiax.chats.domain.models.participantKey;

import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.KeyVersion;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public class ParticipantKey {
    private final ParticipantKeyId id;
    private final ParticipantId participantId;
    private final DeviceId deviceId;
    private final KeyVersion keyVersion;
    private final EncryptionKey encryptionKey;

    private ParticipantKey(
            ParticipantKeyId id,
            ParticipantId participantId,
            DeviceId deviceId,
            KeyVersion keyVersion,
            EncryptionKey encryptionKey
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.participantId = Require.notNull(participantId, DomainErrorCode.MISSING_REQUIRED_FIELD, "participantId");
        this.deviceId = Require.notNull(deviceId, DomainErrorCode.MISSING_REQUIRED_FIELD, "deviceId");
        this.keyVersion = Require.notNull(keyVersion, DomainErrorCode.MISSING_REQUIRED_FIELD, "keyVersion");
        this.encryptionKey = Require.notNull(encryptionKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "encryptionKey");
    }

    public static ParticipantKey of(
            ParticipantKeyId id,
            ParticipantId participantId,
            DeviceId deviceId,
            KeyVersion keyVersion,
            EncryptionKey encryptionKey
    ) {
        return new ParticipantKey(id, participantId, deviceId, keyVersion, encryptionKey);
    }

    public static ParticipantKey create(
            ParticipantId participantId,
            DeviceId deviceId,
            EncryptionKey encryptionKey
    ) {
        return new ParticipantKey(
                ParticipantKeyId.create(),
                participantId,
                deviceId,
                KeyVersion.create(),
                encryptionKey
        );
    }

    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    public KeyVersion getKeyVersion() {
        return keyVersion;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public ParticipantId getParticipantId() {
        return participantId;
    }

    public ParticipantKeyId getId() {
        return id;
    }
}
