package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceUseCase;
import com.paranoiax.users.application.ports.in.devices.revoke.RevokeDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.revoke.RevokeDeviceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/devices")
public class DeviceController {
    private final RegisterDeviceUseCase registerDeviceUseCase;
    private final RevokeDeviceUseCase revokeDeviceUseCase;

    @PutMapping("/{device_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("device_id") UUID deviceId,
            @Valid @RequestBody RegisterDeviceRequest request
    ) {
        registerDeviceUseCase.execute(new RegisterDeviceCommand(
                request.migrationId(),
                deviceId,
                request.deviceName(),
                request.deviceType(),
                request.signature(),
                request.identityKey(),
                request.encryptionKey(),
                request.deviceSignature(),
                idempotencyKey
        ));
    }

    @DeleteMapping("/{device_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(
            @PathVariable("device_id") UUID deviceId,
            @AuthenticationPrincipal UUID userId
    ) {
        revokeDeviceUseCase.execute(new RevokeDeviceCommand(userId, deviceId));
    }
}