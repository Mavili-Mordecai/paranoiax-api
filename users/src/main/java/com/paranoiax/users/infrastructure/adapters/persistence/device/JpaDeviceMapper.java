package com.paranoiax.users.infrastructure.adapters.persistence.device;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.IdentityKey;
import com.paranoiax.core_infra.operationResultMapper.OperationResultsMapper;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceName;
import com.paranoiax.users.domain.models.device.DeviceSignature;
import com.paranoiax.users.infrastructure.entities.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaDeviceMapper implements OperationResultsMapper<Device, DeviceEntity> {
    @Override
    public Class<Device> getDomainClass() {
        return Device.class;
    }

    @Override
    public Class<DeviceEntity> getEntityClass() {
        return DeviceEntity.class;
    }

    @Override
    public Device toDomain(DeviceEntity entity) {
        return Device.of(
                new DeviceId(entity.getId()),
                new UserId(entity.getUserId()),
                new DeviceName(entity.getName()),
                entity.getType(),
                new IdentityKey(entity.getIdentityKey()),
                new EncryptionKey(entity.getEncryptionKey()),
                new DeviceSignature(entity.getDeviceSignature()),
                entity.getRevokedAt(),
                entity.getLastSeenAt(),
                entity.getCreatedAt()
        );
    }

    @Override
    public DeviceEntity toEntity(Device device) {
        return DeviceEntity.builder()
                .id(device.getId().value())
                .userId(device.getUserId().value())
                .name(device.getName().value())
                .type(device.getType())
                .identityKey(device.getIdentityKey().value())
                .encryptionKey(device.getEncryptionKey().value())
                .deviceSignature(device.getDeviceSignature().value())
                .revokedAt(device.getRevokedAt())
                .lastSeenAt(device.getLastActivityAt())
                .createdAt(device.getCreatedAt())
                .build();
    }
}
