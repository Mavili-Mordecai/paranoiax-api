package com.paranoiax.notifications.infrastructure.adapters.persistence.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "fcm_tokens",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_user_device",
                        columnNames = {"user_id", "device_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Column(name = "updated_at")
    private Instant updatedAt;

    
    @PrePersist
    @PreUpdate
    public void updateTimeStamps() {
        this.updatedAt = Instant.now();
    }
}