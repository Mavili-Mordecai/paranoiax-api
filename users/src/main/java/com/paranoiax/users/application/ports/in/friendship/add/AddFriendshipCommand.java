package com.paranoiax.users.application.ports.in.friendship.add;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record AddFriendshipCommand(
        UUID userId,
        UUID friendId,
        String attributes,
        List<FriendshipKeyInfo> keys,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                userId.toString(),
                friendId.toString(),
                attributes,
                keys.stream().map(FriendshipKeyInfo::sharedKey).collect(Collectors.joining(",")),
                operationId
        );
    }
}