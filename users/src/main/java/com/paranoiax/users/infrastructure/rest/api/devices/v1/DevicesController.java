package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/devices")
public class DevicesController {

    @PostMapping("/migrations/upload-url")
    public ResponseEntity<CreateUploadUrlResponse> createUploadUrl(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUploadUrlResponse(
                "test-blob-id",
                "test-upload-url"
        ));
    }

    @PostMapping("/link-token")
    public ResponseEntity<CreateLinkTokenResponse> createLinkToken(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody CreateLinkTokenRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateLinkTokenResponse(
                "test-link-token",
                "test-challenge",
                1000L
        ));
    }

    @PostMapping("/migrations/download-url")
    public ResponseEntity<Resource> createDownloadUrl(@Valid @RequestBody DownloadBlobRequest request) {
        byte[] testData = "TestData".getBytes(StandardCharsets.UTF_8);
        Resource resource = new ByteArrayResource(testData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"blob\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PutMapping("/{device_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("device_id") UUID deviceId,
            @Valid @RequestBody RegisterDeviceRequest request
    ) {
    }

    @DeleteMapping("/{device_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(
            @PathVariable("device_id") UUID deviceId
    ) {
    }
}