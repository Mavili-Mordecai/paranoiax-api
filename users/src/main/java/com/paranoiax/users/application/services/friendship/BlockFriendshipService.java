package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.exceptions.InvalidFriendOperationException;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.friendship.block.BlockFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.block.BlockFriendshipUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.friendship.Friendship;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;

@Slf4j
public class BlockFriendshipService implements BlockFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public BlockFriendshipService(
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
    public void execute(BlockFriendshipCommand command) {
        executor.execute(command, Friendship.class, lockTtl, resultTtl, () -> {
            if (command.friendId().equals(command.userId())) {
                throw new InvalidFriendOperationException();
            }

            UserId userId = new UserId(command.userId());
            UserId friendId = new UserId(command.friendId());

            Optional<Friendship> friendship = friendshipPort.findByUserAndFriend(userId, friendId);

            if (friendship.isPresent()) {
                Friendship existing = friendship.get();
                existing.block();
                return friendshipPort.update(existing);
            }

            return friendshipPort.insert(Friendship.block(userId, friendId));
        });
    }
}