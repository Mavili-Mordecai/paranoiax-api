package com.paranoiax.users.application.ports.out;

import java.time.Duration;
import java.util.UUID;

public interface MediaStoragePort {
    String generateUploadUrl(UUID blobId, Duration ttl);
    String generateDownloadUrl(UUID blobId, Duration ttl);
}