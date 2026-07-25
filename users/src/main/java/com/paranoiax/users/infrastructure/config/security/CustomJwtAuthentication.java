package com.paranoiax.users.infrastructure.config.security;

import com.paranoiax.users.domain.models.device.DeviceType;
import com.paranoiax.users.domain.models.user.UserType;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
public class CustomJwtAuthentication extends AbstractAuthenticationToken {
    private final UUID userId;
    private final UUID deviceId;
    private final DeviceType deviceType;

    public CustomJwtAuthentication(UUID userId, UserType userType, UUID deviceId, DeviceType deviceType) {
        super(List.of(new SimpleGrantedAuthority("ROLE_" + userType.name())));
        this.userId = userId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return userId;
    }
}