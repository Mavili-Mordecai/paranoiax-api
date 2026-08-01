package com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl;

public record DeviceMigrationDownloadUrlResult(
        String downloadUrl,
        String deviceSignature
) {
}