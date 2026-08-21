package com.paranoiax.users.application.ports.in.friendship.add;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.List;
import java.util.UUID;

public record AddFriendshipCommand(
        UUID userId,
        UUID friendId,
        String attributes,
        List<FriendshipKeyInfo> keys,
        String operationId
) implements OperationCommand {
}