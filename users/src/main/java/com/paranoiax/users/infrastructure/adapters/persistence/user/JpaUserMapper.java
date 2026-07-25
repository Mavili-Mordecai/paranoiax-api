package com.paranoiax.users.infrastructure.adapters.persistence.user;

import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.ImageUrl;
import com.paranoiax.users.domain.models.user.*;
import com.paranoiax.users.infrastructure.common.OperationResultsMapper;
import com.paranoiax.users.infrastructure.persistence.entities.AvatarEntity;
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
                .firstName(profile != null ? profile.firstName() : null)
                .lastName(profile != null ? profile.lastName() : null)
                .bio(profile != null ? profile.bio() : null)
                .invitedById(user.getInvitedBy().value())
                .avatar(toEntityAvatar(user.getAvatar()))
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
                toDomainProfile(entity),
                new UserId(entity.getInvitedById()),
                toDomainAvatar(entity.getAvatar()),
                entity.getLastSeenAt(),
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }

    private Avatar toDomainAvatar(AvatarEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Avatar(
                new ImageUrl(entity.getSmall()),
                new ImageUrl(entity.getMedium()),
                new ImageUrl(entity.getLarge()),
                entity.getCreatedAt()
        );
    }

    private AvatarEntity toEntityAvatar(Avatar avatar) {
        if (avatar == null) {
            return null;
        }

        return AvatarEntity.builder()
                .small(avatar.getSmall().value())
                .medium(avatar.getMedium().value())
                .large(avatar.getLarge().value())
                .createdAt(avatar.getCreatedAt())
                .build();
    }

    private Profile toDomainProfile(UserEntity entity) {
        if (isNullOrBlank(entity.getFirstName()) && isNullOrBlank(entity.getLastName()) && isNullOrBlank(entity.getBio())) {
            return null;
        }

        return new Profile(entity.getFirstName(), entity.getLastName(), entity.getBio());
    }

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}
