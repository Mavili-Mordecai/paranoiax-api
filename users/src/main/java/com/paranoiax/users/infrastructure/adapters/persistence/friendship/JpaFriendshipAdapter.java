package com.paranoiax.users.infrastructure.adapters.persistence.friendship;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.infrastructure.persistence.entities.FriendshipEntity;
import com.paranoiax.users.infrastructure.persistence.repositories.JpaFriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFriendshipAdapter implements FriendshipPort {
    private final JpaFriendshipRepository repository;
    private final JpaFriendshipMapper mapper;

    @Override
    public Friendship insert(Friendship friendship) {
        return mapper.toDomain(repository.save(mapper.toEntity(friendship)));
    }

    @Override
    public Friendship update(Friendship friendship) {
        FriendshipEntity entity = mapper.toEntity(friendship);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Friendship> find(FriendshipId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Friendship> findByUser(UserId userId, Long updatedAfter, Integer limit, Integer offset) {
        return repository.findByUser(
                userId.value(),
                Instant.ofEpochMilli(updatedAfter),
                limit,
                offset
        ).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Friendship> findBetween(UserId userId, UserId friendId) {
        return repository.findBetween(userId.value(), friendId.value()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Friendship> findByUserAndFriend(UserId userId, UserId friendId) {
        return repository.findAllByUserIdAndFriendId(userId.value(), friendId.value()).map(mapper::toDomain);
    }

    @Override
    public void delete(FriendshipId id) {
        repository.deleteById(id.value());
    }
}