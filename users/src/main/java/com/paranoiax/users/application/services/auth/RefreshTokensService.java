package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.RefreshToken;
import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.application.ports.in.auth.refreshTokens.RefreshTokensCommand;
import com.paranoiax.users.application.ports.in.auth.refreshTokens.RefreshTokensUseCase;
import com.paranoiax.users.application.ports.out.AuthTokenBlacklistPort;
import com.paranoiax.users.application.ports.out.AuthTokenPort;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.exceptions.RevokedException;
import com.paranoiax.users.domain.models.device.Device;

import java.time.Duration;

public class RefreshTokensService implements RefreshTokensUseCase {
    private final AuthTokenPort authTokenPort;
    private final AuthTokenBlacklistPort blacklistPort;
    private final DevicePort devicePort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public RefreshTokensService(
            AuthTokenPort authTokenPort,
            AuthTokenBlacklistPort blacklistPort,
            DevicePort devicePort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.authTokenPort = authTokenPort;
        this.blacklistPort = blacklistPort;
        this.devicePort = devicePort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public TokenPair execute(RefreshTokensCommand command) {
        RefreshToken refreshToken = authTokenPort.parseRefreshToken(command.refreshToken());

        return executor.execute(command, TokenPair.class, lockTtl, resultTtl, () -> {
            if (blacklistPort.contains(refreshToken.getId())) {
                throw new RevokedException("Refresh token");
            }

            Device device = devicePort.findById(refreshToken.getDeviceId())
                    .orElseThrow(() -> new NotFoundException("Device"));

            if (!blacklistPort.addIfAbsent(refreshToken.getId(), refreshToken.getRemainingTtl())) {
                throw new RevokedException("Refresh token");
            }

            return authTokenPort.generateTokenPair(device);
        });
    }
}