package com.paranoiax.users.application.ports.in.friendship.getKeys;

public interface GetFriendshipKeysUseCase {
    FriendshipKeyResult execute(GetFriendshipKeysQuery query);
}