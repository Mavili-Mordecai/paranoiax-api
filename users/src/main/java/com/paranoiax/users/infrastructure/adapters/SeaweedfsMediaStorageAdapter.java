package com.paranoiax.users.infrastructure.adapters;

import com.paranoiax.users.application.ports.out.MediaStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SeaweedfsMediaStorageAdapter implements MediaStoragePort {
    private final S3Presigner s3Presigner;

    @Value("${s3.migration.bucket}")
    private String migrationBucketName;

    @Override
    public String generateUploadUrl(UUID blobId, Duration ttl) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(migrationBucketName)
                .key(blobId.toString())
                .contentType("application/octet-stream")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(request)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        return presignedRequest.url().toString();
    }

    @Override
    public String generateDownloadUrl(UUID blobId, Duration ttl) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(migrationBucketName)
                .key(blobId.toString())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }
}