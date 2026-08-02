package com.paranoiax.users.infrastructure.adapters.persistence.deviceMigration;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.DeviceSignature;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationStatus;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisDeviceMigrationMapper implements OperationResultsMapper<DeviceMigration, RedisDeviceMigrationDto> {
    @Override
    public Class<DeviceMigration> getDomainClass() {
        return DeviceMigration.class;
    }

    @Override
    public Class<RedisDeviceMigrationDto> getEntityClass() {
        return RedisDeviceMigrationDto.class;
    }

    @Override
    public RedisDeviceMigrationDto toEntity(DeviceMigration migration) {
        return new RedisDeviceMigrationDto(
                migration.getId().value(),
                migration.getDeviceId().value(),
                migration.getUserId().value(),
                migration.getBlobId(),
                migration.getChallenge() != null ? migration.getChallenge().value() : null,
                migration.getStatus().name(),
                migration.getIdentityKey().value(),
                migration.getEncryptionKey().value(),
                migration.getDeviceSignature().value(),
                migration.getCreatedAt(),
                migration.getExpiresAt()
        );
    }

    @Override
    public DeviceMigration toDomain(RedisDeviceMigrationDto entity) {
        return DeviceMigration.of(
                new DeviceMigrationId(entity.getId()),
                new DeviceId(entity.getDeviceId()),
                new UserId(entity.getUserId()),
                entity.getBlobId(),
                entity.getChallenge() != null ? new ChallengeValue(entity.getChallenge()) : null,
                DeviceMigrationStatus.valueOf(entity.getStatus()),
                new IdentityKey(entity.getIdentityKey()),
                new EncryptionKey(entity.getEncryptionKey()),
                new DeviceSignature(entity.getDeviceSignature()),
                entity.getCreatedAt(),
                entity.getExpiresAt()
        );
    }
}