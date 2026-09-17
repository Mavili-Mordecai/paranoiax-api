package com.paranoiax.chats.infrastructure.rest.api.invites.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paranoiax.chats.application.ports.in.invite.create.InviteDetails;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InviteResponse(
        String inviteId,
        String chatId,
        Integer maxUses,
        Integer usesCount,
        Instant expiresAt
) {
    public static InviteResponse from(InviteDetails invite) {
        return new InviteResponse(
                invite.inviteId().value().toString(),
                invite.chatId().value().toString(),
                invite.maxUses() != null ? invite.maxUses().value() : null,
                invite.usesCount() != null ? invite.usesCount().value() : null,
                invite.expiresAt()
        );
    }
}
