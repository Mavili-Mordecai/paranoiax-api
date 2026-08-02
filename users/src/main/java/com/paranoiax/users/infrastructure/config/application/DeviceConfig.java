package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceUseCase;
import com.paranoiax.users.application.ports.in.devices.revoke.RevokeDeviceUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.devices.RegisterDeviceService;
import com.paranoiax.users.application.services.devices.RevokeDeviceService;
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
            EventPublisher eventPublisher,
            OperationExecutor executor,
            @Value("${s3.lock-ttl}") Duration lockTtl,
            @Value("${s3.result-ttl}") Duration resultTtl
    ) {
        return new CreateDeviceMigrationService(
                mediaStoragePort,
                deviceMigrationPort,
                userPort,
                eventPublisher,
                executor,
                lockTtl,
                resultTtl
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
            EventPublisher eventPublisher,
            OperationExecutor executor,
            @Value("${application.devices.migrations.complete-upload.lock-ttl}") Duration lockTtl,
            @Value("${application.devices.migrations.complete-upload.result-ttl}") Duration resultTtl,
            @Value("${application.devices.migrations.complete-upload.token-size}") int tokenSize
    ) {
        return new CompleteDeviceMigrationUploadService(
                deviceMigrationPort,
                tokenGenerator,
                eventPublisher,
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
            SignatureVerifierPort verifierPort,
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

    @Bean
    public RegisterDeviceUseCase registerDeviceUseCase(
            DeviceMigrationPort deviceMigrationPort,
            DevicePort devicePort,
            SignatureVerifierPort verifierPort,
            OperationExecutor operationExecutor,
            @Value("${application.devices.register.lock-ttl}") Duration lockTtl,
            @Value("${application.devices.register.result-ttl}") Duration resultTtl
    ) {
        return new RegisterDeviceService(
                deviceMigrationPort,
                devicePort,
                verifierPort,
                operationExecutor,
                lockTtl,
                resultTtl
        );
    }

    @Bean
    public RevokeDeviceUseCase revokeDeviceUseCase(
            DevicePort devicePort,
            TransactionPort transactionPort
    ) {
        return new RevokeDeviceService(devicePort, transactionPort);
    }
}