package com.paranoiax.users.application.ports.out;

import java.util.UUID;

public interface S3Port {
    String generateUploadUrl(UUID blobId);
    String generateDownloadUrl(UUID blobId);
}