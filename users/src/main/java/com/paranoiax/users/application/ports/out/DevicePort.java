package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;

import java.util.List;

public interface DevicePort {
    Device insert(Device device);
    Device update(Device device);
    List<Device> findDevicesByUserId(UserId userId);
    void deleteById(DeviceId deviceId);
}
