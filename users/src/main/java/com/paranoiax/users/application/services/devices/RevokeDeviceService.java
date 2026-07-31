package com.paranoiax.users.application.services.devices;

import com.paranoiax.users.application.ports.in.devices.revoke.RevokeDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.revoke.RevokeDeviceUseCase;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.domain.exceptions.AccessDeniedException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;

public class RevokeDeviceService implements RevokeDeviceUseCase {
    private final DevicePort devicePort;
    private final TransactionPort transactionPort;

    public RevokeDeviceService(DevicePort devicePort, TransactionPort transactionPort) {
        this.devicePort = devicePort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(RevokeDeviceCommand command) {
        transactionPort.execute(() -> {
            Device device = devicePort.findById(new DeviceId(command.deviceId()))
                    .orElseThrow(() -> new NotFoundException("Device"));

            if (!device.getUserId().value().equals(command.userId())) {
                throw new AccessDeniedException();
            }

            device.revoke();

            devicePort.update(device);
        });
    }
}