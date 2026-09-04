package com.paranoiax.chats.infrastructure.adapters.persistence.groupProfile;

import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileAvatar;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileId;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileName;
import com.paranoiax.chats.infrastructure.entities.GroupProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaGroupProfileMapper {
    public GroupProfile toDomain(GroupProfileEntity entity) {
        return GroupProfile.of(
                new GroupProfileId(entity.getId()),
                new GroupProfileName(entity.getName()),
                entity.getIconLarge() == null || entity.getIconSmall() == null
                        ? null
                        : new GroupProfileAvatar(entity.getIconSmall(), entity.getIconLarge()),
                entity.getUpdatedAt()
        );
    }

    public GroupProfileEntity toEntity(GroupProfile domain) {
        return GroupProfileEntity.builder()
                .id(domain.getId().value())
                .name(domain.getName().value())
                .iconSmall(domain.getAvatar() == null ? null : domain.getAvatar().small())
                .iconLarge(domain.getAvatar() == null ? null : domain.getAvatar().large())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
