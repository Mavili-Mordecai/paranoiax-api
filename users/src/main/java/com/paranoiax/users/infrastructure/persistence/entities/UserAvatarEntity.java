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
@Table(name = "users_avatars", schema = "users")
public class UserAvatarEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String small;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String medium;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String large;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Builder.Default
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return false;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
