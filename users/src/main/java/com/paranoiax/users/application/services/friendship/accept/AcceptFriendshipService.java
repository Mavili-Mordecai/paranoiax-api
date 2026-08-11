package com.paranoiax.users.application.services.friendship.accept;

import com.paranoiax.core.domain.exceptions.InvalidStateTransitionException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.application.ports.in.friendship.accept.AcceptFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.accept.AcceptFriendshipUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Duration;

public class AcceptFriendshipService implements AcceptFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public AcceptFriendshipService(
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
    public void execute(AcceptFriendshipCommand command) {
        executor.execute(command, Friendship.class, lockTtl, resultTtl, () -> {
            Friendship income = friendshipPort.findById(new FriendshipId(command.id()))
                    .orElseThrow(() -> new NotFoundException("Friendship"));

            if (income.getStatus() == FriendshipStatus.DELETED || !income.getUserId().value().equals(command.userId())) {
                throw new NotFoundException("Friendship");
            }

            if (income.getStatus() != FriendshipStatus.INCOME) {
                throw new InvalidStateTransitionException(income.getStatus().name(), FriendshipStatus.ACCEPTED.name());
            }

            Friendship outcome = friendshipPort.findByUserAndFriend(income.getFriendId(), income.getUserId())
                    .orElseThrow(() -> new NotFoundException("Friendship"));

            if (outcome.getStatus() != FriendshipStatus.OUTCOME) {
                throw new InvalidStateTransitionException(outcome.getStatus().name(), FriendshipStatus.ACCEPTED.name());
            }

            income.accept();
            outcome.accept();

            friendshipPort.update(income);
            friendshipPort.update(outcome);

            return income;
        });
    }
}