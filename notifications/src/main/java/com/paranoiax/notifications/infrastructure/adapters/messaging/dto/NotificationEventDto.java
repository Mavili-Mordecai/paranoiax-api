package com.paranoiax.notifications.infrastructure.adapters.messaging.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record NotificationEventDto(
        UUID eventId,
        List<UUID> recipientUserIds,
        NotificationPayload notification,
        Map<String, String> data,
        Options options
) {
    public record NotificationPayload(String title, String body, String imageUrl) {}
    public record Options(String priority, Integer ttlSeconds, String collapseKey) {}
}