package com.paranoiax.notifications.infrastructure.adapters.cache;

import com.paranoiax.notifications.infrastructure.adapters.persistence.repositories.JpaFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTokenCacheAdapter {

    private final StringRedisTemplate redisTemplate;
     private final JpaFcmTokenRepository tokenRepository;

    private static final String KEY_PREFIX = "user_tokens:";
    private static final Duration TTL = Duration.ofDays(7);

    /**
     * Получает токены пользователя, используя политику Sliding Expiration
     */
    public List<String> getUserTokens(UUID userId) {
        String key = KEY_PREFIX + userId.toString();

        try {
            Set<String> cachedTokens = redisTemplate.opsForSet().members(key);

            if (cachedTokens != null && !cachedTokens.isEmpty()) {
                redisTemplate.expire(key, TTL);
                log.debug("Cache Hit для {}. TTL обновлен.", userId);
                return new ArrayList<>(cachedTokens);
            }
        } catch (Exception e) {
            log.error("Ошибка при чтении из Redis для пользователя {}. Идем в БД.", userId, e);
        }

        log.debug("Cache Miss для {}. Запрашиваем токены из БД.", userId);
        List<String> dbTokens = tokenRepository.findAllTokensByUserId(userId);

        if (!dbTokens.isEmpty()) {
            try {
                redisTemplate.opsForSet().add(key, dbTokens.toArray(new String[0]));
                redisTemplate.expire(key, TTL);
            } catch (Exception e) {
                log.warn("Не удалось сохранить токены в Redis для {}", userId, e);
            }
        }

        return dbTokens;
    }
}