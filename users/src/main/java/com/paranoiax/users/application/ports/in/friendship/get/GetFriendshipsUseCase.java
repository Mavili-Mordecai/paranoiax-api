package com.paranoiax.users.application.ports.in.friendship.get;

public interface GetFriendshipsUseCase {
    FriendshipResult execute(GetFriendshipsQuery query);
}