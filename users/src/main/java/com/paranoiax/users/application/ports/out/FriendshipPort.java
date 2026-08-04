package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.util.List;
import java.util.Optional;

public interface FriendshipPort {
    Friendship insert(Friendship friendship);
    Friendship update(Friendship friendship);
    List<Friendship> findAllByUserId(UserId userId);
    List<Friendship> findAllByUserIdAndStatus(UserId userId, FriendshipStatus status);
    Optional<Friendship> findByUserIdAndFriendId(UserId userId, UserId friendId);
    void delete(FriendshipId id);
}
