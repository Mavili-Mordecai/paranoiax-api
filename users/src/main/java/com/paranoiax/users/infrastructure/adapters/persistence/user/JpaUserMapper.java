package com.paranoiax.users.infrastructure.adapters.persistence.user;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.user.*;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import com.paranoiax.users.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaUserMapper implements OperationResultsMapper<User, UserEntity> {

    @Override
    public Class<User> getDomainClass() {
        return User.class;
    }

    @Override
    public Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    public UserEntity toEntity(User user) {
        Profile profile = user.getProfile();

        return UserEntity.builder()
                .id(user.getId().value())
                .identityKey(user.getIdentityKey().value())
                .username(user.getUsername().value())
                .type(user.getType())
                .profile(profile != null ? profile.data() : null)
                .profileVersion(profile != null ? profile.version() : null)
                .invitedById(user.getInvitedBy() != null ? user.getInvitedBy().value() : null)
                .lastSeenAt(user.getLastSeenAt())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public User toDomain(UserEntity entity) {
        return User.of(
                new UserId(entity.getId()),
                new IdentityKey(entity.getIdentityKey()),
                new Username(entity.getUsername()),
                entity.getType(),
                isNullOrBlank(entity.getProfile()) && entity.getProfileVersion() == 0
                        ? null
                        : new Profile(entity.getProfile(), entity.getProfileVersion()),
                entity.getInvitedById() != null ? new UserId(entity.getInvitedById()) : null,
                entity.getLastSeenAt(),
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}
