package com.paranoiax.notifications.infrastructure.adapters.push;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FirebasePushAdapter {

    /**
     * Отправляет уведомление и возвращает список "мертвых" токенов,
     * которые нужно удалить из нашей базы данных.
     */
    public List<String> sendMulticast(List<String> tokens, String title, String body) {
        if (tokens == null || tokens.isEmpty()) {
            return List.of();
        }

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .addAllTokens(tokens)
                .build();

        List<String> deadTokens = new ArrayList<>();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

            log.info("Отправлено пушей: {}. Успешно: {}. С ошибкой: {}",
                    tokens.size(), response.getSuccessCount(), response.getFailureCount());


            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();

                for (int i = 0; i < responses.size(); i++) {
                    SendResponse sendResponse = responses.get(i);

                    if (!sendResponse.isSuccessful()) {
                        MessagingErrorCode errorCode = sendResponse.getException().getMessagingErrorCode();

                        if (errorCode == MessagingErrorCode.UNREGISTERED || errorCode == MessagingErrorCode.INVALID_ARGUMENT) {
                            String invalidToken = tokens.get(i);
                            log.warn("Найден невалидный токен (будет удален): {}", invalidToken);
                            deadTokens.add(invalidToken);
                        }
                    }
                }
            }
        } catch (FirebaseMessagingException e) {
            log.error("Глобальная ошибка при отправке пуш-уведомлений в Firebase", e);
        }

        return deadTokens;
    }
}