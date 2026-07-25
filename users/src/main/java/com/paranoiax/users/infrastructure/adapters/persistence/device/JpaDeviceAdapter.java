package com.paranoiax.users.infrastructure.adapters.persistence.device;

import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;
import com.paranoiax.users.infrastructure.persistence.entities.DeviceEntity;
import com.paranoiax.users.infrastructure.persistence.repositories.JpaDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Device> findDevicesByUserId(UserId userId) {
        return repository.findByUserId(userId.value()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(DeviceId deviceId) {
        repository.deleteById(deviceId.value());
    }
}
