package com.paranoiax.notifications.infrastructure.adapters.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisIdempotencyAdapter {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "processed_event:";
    private static final Duration TTL = Duration.ofHours(24);

    /**
     * @return true - если событие обрабатывается впервые, false - если это дубликат
     */
    public boolean isNewEvent(UUID eventId) {
        if (eventId == null) {
            log.warn("Получен пустой eventId, невозможно проверить идемпотентность");
            return false; // Защита от дурака
        }

        String key = KEY_PREFIX + eventId.toString();

        try {
            Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, "PROCESSED", TTL);

            if (Boolean.TRUE.equals(isNew)) {
                log.debug("Событие {} новое. Начинаем обработку.", eventId);
                return true;
            } else {
                log.warn("Событие {} уже было обработано (дубликат). Игнорируем.", eventId);
                return false;
            }
        } catch (Exception e) {

            log.error("Ошибка при обращении к Redis для проверки события {}", eventId, e);
            throw new RuntimeException("Redis недоступен для проверки идемпотентности", e);
        }
    }
}