package com.paranoiax.chats.infrastructure.rest.api.keys.v1;

import com.paranoiax.chats.application.ports.in.key.findAll.PendingKeyDetails;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PendingKeyResponse(
        String id,
        String chatId,
        Integer keyVersion,
        String encryptedKey
) {
    public static PendingKeyResponse from(PendingKeyDetails details) {
        return new PendingKeyResponse(
                details.id().value().toString(),
                details.chatId().value().toString(),
                details.keyVersion().value(),
                details.encryptionKey().value()
        );
    }
}
