package com.paranoiax.notifications.infrastructure.adapters.messaging;

import com.paranoiax.notifications.infrastructure.adapters.cache.RedisIdempotencyAdapter;
import com.paranoiax.notifications.infrastructure.adapters.cache.RedisTokenCacheAdapter;
import com.paranoiax.notifications.infrastructure.adapters.push.FirebasePushAdapter;
import com.paranoiax.notifications.infrastructure.adapters.messaging.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final RedisIdempotencyAdapter idempotencyAdapter;
    private final RedisTokenCacheAdapter tokenCacheAdapter;
    private final FirebasePushAdapter firebasePushAdapter;


    private static final int FIREBASE_MAX_BATCH_SIZE = 500;

    @KafkaListener(
            topics = "notifications.send.topic",
            groupId = "notification-service-group"
    )
    public void processNotificationEvent(NotificationEventDto event) {
        log.info("Получено событие рассылки: {}", event.eventId());

        if (!idempotencyAdapter.isNewEvent(event.eventId())) {
            return;
        }

        try {
            List<String> allTokens = event.recipientUserIds().stream()
                    .flatMap(userId -> tokenCacheAdapter.getUserTokens(userId).stream())
                    .toList();

            if (allTokens.isEmpty()) {
                log.info("Нет ни одного токена для события {}. Рассылка отменена.", event.eventId());
                return;
            }


            List<List<String>> batches = partitionList(allTokens, FIREBASE_MAX_BATCH_SIZE);
            log.info("Токенов собрано: {}. Разбито на {} батчей.", allTokens.size(), batches.size());

            for (List<String> batch : batches) {
                List<String> deadTokens = firebasePushAdapter.sendMulticast(
                        batch,
                        event.notification().title(),
                        event.notification().body()
                );

                if (!deadTokens.isEmpty()) {
                    log.info("Найдено {} мертвых токенов. Запускаем очистку...", deadTokens.size());

                }
            }

            log.info("Событие {} успешно обработано!", event.eventId());

        } catch (Exception e) {
            log.error("Внутренняя ошибка при обработке события {}. Сбрасываем ключ идемпотентности.", event.eventId(), e);
            throw e;
        }
    }

    /**
     * Вспомогательный метод для разбиения огромного списка на подсписки.
     * Не требует сторонних библиотек вроде Guava. Использует встроенный subList.
     */
    private List<List<String>> partitionList(List<String> list, int batchSize) {
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            int end = Math.min(i + batchSize, list.size());
            partitions.add(list.subList(i, end));
        }
        return partitions;
    }
}