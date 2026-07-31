package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/devices")
public class DeviceController {
    private final RegisterDeviceUseCase registerDeviceUseCase;

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
            @PathVariable("device_id") UUID deviceId
    ) {
    }
}