package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;

import java.util.List;
import java.util.Optional;

public interface FriendshipPort {
    Friendship insert(Friendship friendship);
    Friendship update(Friendship friendship);
    Optional<Friendship> find(FriendshipId id);
    List<Friendship> findByUser(UserId userId, Long updatedAfter, Integer limit, Integer offset);
    List<Friendship> findBetween(UserId userId, UserId friendId);
    Optional<Friendship> findByUserAndFriend(UserId userId, UserId friendId);
    void delete(FriendshipId id);
}
