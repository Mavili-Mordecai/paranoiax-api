package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.user.User;

public interface AuthTokensPort {
    String generateAccessToken(User user, Device device);
    String generateRefreshToken(User user, Device device);
}