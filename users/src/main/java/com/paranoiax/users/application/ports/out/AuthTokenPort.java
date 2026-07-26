package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.application.ports.in.auth.AccessToken;
import com.paranoiax.users.application.ports.in.auth.RefreshToken;
import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.domain.models.device.Device;

public interface AuthTokenPort {
    TokenPair generateTokenPair(Device device);
    AccessToken parseAccessToken(String token);
    RefreshToken parseRefreshToken(String token);
}