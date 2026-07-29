package com.paranoiax.users.infrastructure.adapters;

import com.paranoiax.users.application.ports.out.S3Port;
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
public class SeaweedfsS3Adapter implements S3Port {
    private final S3Presigner s3Presigner;

    @Value("${s3.migration.bucket}")
    private String migrationBucketName;

    @Value("${s3.migration.upload-url.ttl}")
    private Duration uploadUrlTtl;

    @Value("${s3.migration.download-url.ttl}")
    private Duration downloadUrlTtl;

    @Override
    public String generateUploadUrl(UUID blobId) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(migrationBucketName)
                .key(blobId.toString())
                .contentType("application/octet-stream")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(uploadUrlTtl)
                .putObjectRequest(request)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        return presignedRequest.url().toString();
    }

    @Override
    public String generateDownloadUrl(UUID blobId) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(migrationBucketName)
                .key(blobId.toString())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(downloadUrlTtl)
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }
}