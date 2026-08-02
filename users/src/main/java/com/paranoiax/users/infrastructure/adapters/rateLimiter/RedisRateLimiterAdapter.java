package com.paranoiax.users.infrastructure.adapters.rateLimiter;

import com.paranoiax.users.application.ports.out.rateLimiter.RateLimitResult;
import com.paranoiax.users.application.ports.out.rateLimiter.RateLimiter;
import com.paranoiax.users.domain.exceptions.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisRateLimiterAdapter implements RateLimiter {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void consume(String key, Duration window) {
        consume(key, 1, window);
    }

    @Override
    public void consume(String key, long maxQuota, Duration window) {
        RateLimitResult result = tryConsume(key, maxQuota, window);
        if (!result.consumed()) {
            throw new RateLimitExceededException(result.retryAfterMillis());
        }
    }

    @Override
    public RateLimitResult tryConsume(String key, long maxQuota, Duration window) {
        String redisKey = getKey(key);

        redisTemplate.opsForValue().setIfAbsent(redisKey, "0", window);

        Long current = redisTemplate.opsForValue().increment(redisKey);

        Long expire = redisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
        long remainingTtl = expire != null ? Math.max(0, expire) : 0L;

        if (current != null && current > maxQuota) {
            return RateLimitResult.failed(remainingTtl);
        }

        long remainingQuota = current != null ? maxQuota - current : 0;
        return RateLimitResult.success(remainingQuota, remainingTtl);
    }

    private String getKey(String key) {
        return "rate_limit:" + key;
    }
}
