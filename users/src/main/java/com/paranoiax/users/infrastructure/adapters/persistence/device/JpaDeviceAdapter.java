package com.paranoiax.users.infrastructure.adapters.persistence.device;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.infrastructure.persistence.entities.DeviceEntity;
import com.paranoiax.users.infrastructure.persistence.repositories.JpaDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaDeviceAdapter implements DevicePort {
    private final JpaDeviceRepository repository;
    private final JpaDeviceMapper mapper;

    @Override
    public Device insert(Device device) {
        return mapper.toDomain(repository.save(mapper.toEntity(device)));
    }

    @Override
    public Device update(Device device) {
        DeviceEntity entity = mapper.toEntity(device);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Device> findById(DeviceId deviceId) {
        return repository.findById(deviceId.value()).map(mapper::toDomain);
    }

    @Override
    public List<Device> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Device> findByUserIdIn(Collection<UserId> userIds) {
        return repository.findByUserIdIn(userIds.stream().map(UserId::value).toList()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(DeviceId deviceId) {
        repository.deleteById(deviceId.value());
    }
}
