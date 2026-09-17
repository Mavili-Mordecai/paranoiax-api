package com.paranoiax.chats.application.ports.in.key.findAll;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKey;
import com.paranoiax.chats.domain.models.participantKey.ParticipantKeyId;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.KeyVersion;

public record PendingKeyDetails(
        ParticipantKeyId id,
        ChatId chatId,
        KeyVersion keyVersion,
        EncryptionKey encryptionKey
) {
    public static PendingKeyDetails from(ParticipantKey key, ChatId chatId) {
        return new PendingKeyDetails(
                key.getId(),
                chatId,
                key.getKeyVersion(),
                key.getEncryptionKey()
        );
    }
}
