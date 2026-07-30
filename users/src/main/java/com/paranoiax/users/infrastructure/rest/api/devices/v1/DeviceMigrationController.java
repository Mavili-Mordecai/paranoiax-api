package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.DeviceMigrationStatusResult;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/devices/migrations")
public class DeviceMigrationController {
    private final CreateDeviceMigrationUseCase createDeviceMigrationUseCase;
    private final GetDeviceMigrationStatusUseCase getDeviceMigrationStatusUseCase;

    @PutMapping("/{migration_id}")
    public ResponseEntity<MigrationResponse> createMigration(
            @PathVariable("migration_id") UUID migrationId,
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody CreateMigrationRequest request
    ) {
        String uploadUrl = createDeviceMigrationUseCase.execute(new CreateDeviceMigrationCommand(
                migrationId,
                userId,
                request.identityKey(),
                request.encryptionKey(),
                request.deviceSignature(),
                idempotencyKey
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(new MigrationResponse(uploadUrl));
    }

    @GetMapping("/{migration_id}/status")
    public ResponseEntity<MigrationStatusResponse> getMigrationStatus(
            @PathVariable("migration_id") UUID migrationId
    ) {
        DeviceMigrationStatusResult result = getDeviceMigrationStatusUseCase.execute(migrationId);
        return ResponseEntity.ok(new MigrationStatusResponse(
                result.status().name(),
                result.challenge() != null ? result.challenge().value() : null
        ));
    }

    @PostMapping("/{migration_id}/complete-upload")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeUpload(
            @PathVariable("migration_id") UUID migrationId,
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId
    ) {
    }

    @PostMapping("/{migration_id}/download-url")
    public ResponseEntity<DownloadUrlResponse> generateDownloadUrl(
            @PathVariable("migration_id") UUID migrationId,
            @Valid @RequestBody DownloadUrlRequest request
    ) {
        return ResponseEntity.ok(new DownloadUrlResponse(
                "test-download-url",
                "test-device-signature"
        ));
    }
}
