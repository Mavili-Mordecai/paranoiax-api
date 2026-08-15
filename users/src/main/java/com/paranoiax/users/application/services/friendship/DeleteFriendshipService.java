package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.application.ports.in.friendship.delete.DeleteFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.delete.DeleteFriendshipUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.util.Optional;

public class DeleteFriendshipService implements DeleteFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final TransactionPort transactionPort;

    public DeleteFriendshipService(
            FriendshipPort friendshipPort,
            TransactionPort transactionPort
    ) {
        this.friendshipPort = friendshipPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(DeleteFriendshipCommand command) {
        transactionPort.execute(() -> {
            Friendship target = friendshipPort.find(new FriendshipId(command.id()))
                    .orElseThrow(() -> new NotFoundException("Friendship"));

            if (!target.getUserId().value().equals(command.userId())) {
                throw new NotFoundException("Friendship");
            }

            target.delete();
            friendshipPort.update(target);

            Optional<Friendship> related = friendshipPort.findByUserAndFriend(target.getFriendId(), target.getUserId());
            if (related.isPresent()) {
                Friendship existing = related.get();
                if (existing.getStatus() != FriendshipStatus.BLOCKED) {
                    existing.delete();
                    friendshipPort.update(existing);
                }
            }
        });
    }
}