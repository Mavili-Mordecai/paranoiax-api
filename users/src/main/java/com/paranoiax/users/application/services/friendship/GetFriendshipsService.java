package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.friendship.get.FriendshipDetails;
import com.paranoiax.users.application.ports.in.friendship.get.FriendshipResult;
import com.paranoiax.users.application.ports.in.friendship.get.GetFriendshipsQuery;
import com.paranoiax.users.application.ports.in.friendship.get.GetFriendshipsUseCase;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.domain.models.friendship.Friendship;

import java.time.Instant;
import java.util.List;

public class GetFriendshipsService implements GetFriendshipsUseCase {
    private final FriendshipPort friendshipPort;

    public GetFriendshipsService(FriendshipPort friendshipPort) {
        this.friendshipPort = friendshipPort;
    }

    @Override
    public FriendshipResult execute(GetFriendshipsQuery query) {
        List<Friendship> friendships = friendshipPort.findByUser(
                new UserId(query.userId()),
                query.updatedAfter(),
                query.limit()
        );

        return new FriendshipResult(
                friendships.stream().map(FriendshipDetails::from).toList(),
                friendships.size() < query.limit(),
                Instant.now().toEpochMilli()
        );
    }
}