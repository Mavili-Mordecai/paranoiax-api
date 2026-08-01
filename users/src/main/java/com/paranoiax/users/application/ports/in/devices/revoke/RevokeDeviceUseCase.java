package com.paranoiax.users.application.ports.in.devices.revoke;

public interface RevokeDeviceUseCase {
    void execute(RevokeDeviceCommand command);
}