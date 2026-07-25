package com.paranoiax.users.infrastructure.adapters.persistence.user;

import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.infrastructure.persistence.entities.UserEntity;
import com.paranoiax.users.infrastructure.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUserAdapter implements UserPort {
    private final JpaUserRepository repository;
    private final JpaUserMapper mapper;

    @Override
    public User insert(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }

    @Override
    public User update(User user) {
        UserEntity entity = mapper.toEntity(user);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }
}