package com.paranoiax.users.infrastructure.adapters.persistence.deviceMigration;

import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisDeviceMigrationAdapter implements DeviceMigrationPort {
    private final RedisDeviceMigrationMapper mapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public DeviceMigration insert(DeviceMigration migration, Duration ttl) {
        redisTemplate.opsForValue().set(getKey(migration.getId()), mapper.toEntity(migration), ttl);
        return migration;
    }

    @Override
    public DeviceMigration update(DeviceMigration migration, Duration ttl) {
        redisTemplate.opsForValue().set(getKey(migration.getId()), mapper.toEntity(migration), ttl);
        return migration;
    }

    @Override
    public Optional<DeviceMigration> findById(DeviceMigrationId id) {
        return Optional.ofNullable((RedisDeviceMigrationDto) redisTemplate.opsForValue().get(getKey(id))).map(mapper::toDomain);
    }

    @Override
    public boolean deleteById(DeviceMigrationId id) {
        return Boolean.TRUE.equals(redisTemplate.delete(getKey(id)));
    }

    public String getKey(DeviceMigrationId id) {
        return "device-migration:" + id.value().toString();
    }
}