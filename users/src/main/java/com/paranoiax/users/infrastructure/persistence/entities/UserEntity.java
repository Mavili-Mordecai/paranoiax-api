package com.paranoiax.users.infrastructure.persistence.entities;

import com.paranoiax.core.domain.users.UserType;
import com.paranoiax.users.domain.models.user.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "users")
public class UserEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String identityKey;

    @Column(nullable = false, length = 32, unique = true)
    private String username;

    @Column(nullable = false, length = 4)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(columnDefinition = "TEXT", length = Profile.MAX_SIZE)
    private String profile;

    @Column(nullable = false)
    private Integer profileVersion;

    @Column(name = "invited_by_id", nullable = false)
    private UUID invitedById;

    @Column(nullable = false)
    private Instant lastSeenAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

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