package com.paranoiax.users.infrastructure.rest.api.devices.v1;

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

    @PutMapping("/{migration_id}")
    public ResponseEntity<MigrationResponse> createMigration(
            @PathVariable("migration_id") UUID migrationId,
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody CreateMigrationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new MigrationResponse(
                "test-link-token",
                "test-challenge"
        ));
    }

    @GetMapping("/{migration_id}/status")
    public ResponseEntity<MigrationStatusResponse> getMigrationStatus(
            @PathVariable("migration_id") UUID migrationId
    ) {
        return ResponseEntity.ok(new MigrationStatusResponse(
                "test-status",
                "test-challenge"
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
