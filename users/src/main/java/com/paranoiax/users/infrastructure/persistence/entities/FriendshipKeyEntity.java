package com.paranoiax.users.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friendships_keys", schema = "users")
public class FriendshipKeyEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(name = "friendship_id", nullable = false)
    private UUID friendshipId;

    @Column(name = "friend_device_id", nullable = false)
    private UUID friendDeviceId;

    @Column(columnDefinition = "TEXT")
    private String sharedKey;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}