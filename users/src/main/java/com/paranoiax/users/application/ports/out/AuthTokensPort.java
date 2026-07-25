package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.device.Device;

public interface AuthTokensPort {
    String generateAccessToken(Device device);
    String generateRefreshToken(Device device);
}