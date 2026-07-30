package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationUseCase;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.S3Port;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.devices.migrations.CreateDeviceMigrationService;
import com.paranoiax.users.application.services.devices.migrations.GetDeviceMigrationStatusService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DeviceConfig {

    @Bean
    public CreateDeviceMigrationUseCase createDeviceMigrationUseCase(
            S3Port s3Port,
            UserPort userPort,
            DeviceMigrationPort deviceMigrationPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${s3.lock-ttl}") Duration lockTtl,
            @Value("${s3.result-ttl}") Duration resultTtl
    ) {
         return new CreateDeviceMigrationService(
                 s3Port,
                 userPort,
                 deviceMigrationPort,
                 tokenGenerator,
                 executor,
                 lockTtl,
                 resultTtl
         );
    }

    @Bean
    public GetDeviceMigrationStatusUseCase getDeviceMigrationStatusUseCase(DeviceMigrationPort deviceMigrationPort) {
        return new GetDeviceMigrationStatusService(deviceMigrationPort);
    }
}