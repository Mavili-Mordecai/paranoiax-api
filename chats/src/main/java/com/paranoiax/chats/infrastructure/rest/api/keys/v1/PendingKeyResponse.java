package com.paranoiax.chats.infrastructure.rest.api.keys.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PendingKeyResponse(
        String id,
        String chatId,
        Integer keyVersion,
        String encryptedKey
) {
}
