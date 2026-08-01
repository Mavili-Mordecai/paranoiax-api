package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DevicePort {
    Device insert(Device device);
    Device update(Device device);
    Optional<Device> findById(DeviceId deviceId);
    List<Device> findByUserId(UserId userId);
    List<Device> findByUserIdIn(Collection<UserId> userIds);
    void deleteById(DeviceId deviceId);
}
