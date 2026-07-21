package com.paranoiax.users.infrastructure.persistence.entities;

import com.paranoiax.users.domain.models.device.DeviceType;
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
@Table(name = "users_devices", schema = "users")
public class UserDeviceEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String identityKey;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String encryptionKey;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String deviceSignature;

    @Column(nullable = false)
    private Instant lastSeenAt;

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
