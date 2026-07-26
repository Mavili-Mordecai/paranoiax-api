package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/devices")
public class DevicesController {

    @PostMapping(
            value = "/link-token",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<GenerateLinkTokenResponse> generateLinkToken(
            @RequestPart("encrypted_blob") MultipartFile encryptedBlob,
            @RequestParam("identity_key") String identityKey
    ) {
        if (encryptedBlob.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenerateLinkTokenResponse(
                "test-link-token",
                "test-challenge",
                1000L
        ));
    }

    @PostMapping(
            value = "/link-token/blob",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<Resource> downloadBlob(
            @Valid @RequestBody DownloadBlobRequest request
    ) {
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