package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import com.paranoiax.users.application.ports.out.ChallengeVerifierPort;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.MediaStoragePort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.devices.migrations.CompleteDeviceMigrationUploadService;
import com.paranoiax.users.application.services.devices.migrations.CreateDeviceMigrationService;
import com.paranoiax.users.application.services.devices.migrations.GenerateDeviceMigrationDownloadUrlService;
import com.paranoiax.users.application.services.devices.migrations.GetDeviceMigrationStatusService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DeviceConfig {

    @Bean
    public CreateDeviceMigrationUseCase createDeviceMigrationUseCase(
            MediaStoragePort mediaStoragePort,
            UserPort userPort,
            DeviceMigrationPort deviceMigrationPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${s3.lock-ttl}") Duration lockTtl,
            @Value("${s3.result-ttl}") Duration resultTtl,
            @Value("${application.devices.migrations.migration.token-size}") int tokenSize
    ) {
         return new CreateDeviceMigrationService(
                 mediaStoragePort,
                 deviceMigrationPort,
                 userPort,
                 tokenGenerator,
                 executor,
                 lockTtl,
                 resultTtl,
                 tokenSize
         );
    }

    @Bean
    public GetDeviceMigrationStatusUseCase getDeviceMigrationStatusUseCase(DeviceMigrationPort deviceMigrationPort) {
        return new GetDeviceMigrationStatusService(deviceMigrationPort);
    }

    @Bean
    public CompleteDeviceMigrationUploadService completeDeviceMigrationUploadService(
            DeviceMigrationPort deviceMigrationPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${application.devices.migrations.complete-upload.lock-ttl}") Duration lockTtl,
            @Value("${application.devices.migrations.complete-upload.result-ttl}") Duration resultTtl,
            @Value("${application.devices.migrations.complete-upload.token-size}") int tokenSize
    ) {
        return new CompleteDeviceMigrationUploadService(
                deviceMigrationPort,
                tokenGenerator,
                executor,
                lockTtl,
                resultTtl,
                tokenSize
        );
    }

    @Bean
    public GenerateDeviceMigrationDownloadUrlUseCase generateDeviceMigrationDownloadUrlUseCase(
            MediaStoragePort mediaStoragePort,
            DeviceMigrationPort deviceMigrationPort,
            UserPort userPort,
            ChallengeVerifierPort verifierPort,
            OperationExecutor executor,
            @Value("${application.devices.migrations.generate-download-url.lock-ttl}") Duration lockTtl,
            @Value("${application.devices.migrations.generate-download-url.result-ttl}") Duration resultTtl
    ) {
        return new GenerateDeviceMigrationDownloadUrlService(
                mediaStoragePort,
                deviceMigrationPort,
                userPort,
                verifierPort,
                executor,
                lockTtl,
                resultTtl
        );
    }
}