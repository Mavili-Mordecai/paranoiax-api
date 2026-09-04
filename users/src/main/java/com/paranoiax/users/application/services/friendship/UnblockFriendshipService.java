package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.application.ports.in.friendship.unblock.UnblockFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.unblock.UnblockFriendshipUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Duration;

public class UnblockFriendshipService implements UnblockFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public UnblockFriendshipService(
            FriendshipPort friendshipPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.friendshipPort = friendshipPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(UnblockFriendshipCommand command) {
        executor.execute(command, Friendship.class, lockTtl, resultTtl, () -> {
            Friendship target = friendshipPort.find(new FriendshipId(command.id()))
                    .orElseThrow(() -> new NotFoundException("Friendship"));

            if (!target.getUserId().value().equals(command.userId())) {
                throw new NotFoundException("Friendship");
            }

            FriendshipStatus mirrorStatus = friendshipPort.findByUserAndFriend(target.getFriendId(), target.getUserId())
                    .map(Friendship::getStatus)
                    .orElse(null);

            target.unblock(mirrorStatus);

            return friendshipPort.update(target);
        });
    }
}