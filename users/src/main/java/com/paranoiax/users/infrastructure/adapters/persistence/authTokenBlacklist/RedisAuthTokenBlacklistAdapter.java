package com.paranoiax.users.infrastructure.adapters.persistence.authTokenBlacklist;

import com.paranoiax.users.application.ports.out.AuthTokenBlacklistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisAuthTokenBlacklistAdapter implements AuthTokenBlacklistPort {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean addIfAbsent(UUID tokenId, Duration ttl) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(getTokenKey(tokenId), true, ttl));
    }

    @Override
    public boolean contains(UUID tokenId) {
        return redisTemplate.hasKey(getTokenKey(tokenId));
    }

    private String getTokenKey(UUID tokenId) {
        return "authTokenBlacklist:" + tokenId.toString();
    }
}