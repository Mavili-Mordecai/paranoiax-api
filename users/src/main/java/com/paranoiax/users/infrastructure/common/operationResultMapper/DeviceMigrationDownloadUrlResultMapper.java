package com.paranoiax.users.infrastructure.common.operationResultMapper;

import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.DeviceMigrationDownloadUrlResult;
import org.springframework.stereotype.Component;

@Component
public class DeviceMigrationDownloadUrlResultMapper implements OperationResultsMapper<DeviceMigrationDownloadUrlResult, DeviceMigrationDownloadUrlResult> {
    @Override
    public Class<DeviceMigrationDownloadUrlResult> getDomainClass() {
        return DeviceMigrationDownloadUrlResult.class;
    }

    @Override
    public Class<DeviceMigrationDownloadUrlResult> getEntityClass() {
        return DeviceMigrationDownloadUrlResult.class;
    }

    @Override
    public DeviceMigrationDownloadUrlResult toEntity(DeviceMigrationDownloadUrlResult domain) {
        return domain;
    }

    @Override
    public DeviceMigrationDownloadUrlResult toDomain(DeviceMigrationDownloadUrlResult entity) {
        return entity;
    }
}