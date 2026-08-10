package com.paranoiax.users.application.services.friendship.update;

import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;

import java.time.Duration;

public class UpdateFriendshipService implements UpdateFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public UpdateFriendshipService(
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
    public void execute(UpdateFriendshipCommand command) {
        executor.execute(command, String.class, lockTtl, resultTtl, () -> {
            Friendship friendship = friendshipPort.findById(new FriendshipId(command.id()))
                    .orElseThrow(() -> new NotFoundException("Friendship"));

            if (!friendship.getUserId().value().equals(command.userId())) {
                throw new NotFoundException("Friendship");
            }

            friendship.changeAttributes(command.attributes());

            friendshipPort.update(friendship);

            return friendship.getId().value().toString();
        });
    }
}