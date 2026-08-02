package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.application.AccessToken;
import com.paranoiax.core.application.RefreshToken;
import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.domain.models.device.Device;

public interface AuthTokenPort {
    TokenPair generateTokenPair(Device device);
    AccessToken parseAccessToken(String token);
    RefreshToken parseRefreshToken(String token);
}