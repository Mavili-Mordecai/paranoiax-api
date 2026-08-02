package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.device.Device;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DevicePort {
    Device insert(Device device);
    Device update(Device device);
    Optional<Device> findById(DeviceId deviceId);
    List<Device> findByUserId(UserId userId);
    List<Device> findByUserIdIn(Collection<UserId> userIds);
    void deleteById(DeviceId deviceId);
}
