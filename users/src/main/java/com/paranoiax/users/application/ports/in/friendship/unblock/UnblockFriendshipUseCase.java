package com.paranoiax.users.application.ports.in.friendship.unblock;

public interface UnblockFriendshipUseCase {
    void execute(UnblockFriendshipCommand command);
}