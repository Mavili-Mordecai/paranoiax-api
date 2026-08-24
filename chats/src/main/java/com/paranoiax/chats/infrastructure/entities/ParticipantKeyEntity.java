package com.paranoiax.chats.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats_participants_keys", schema = "chats")
public class ParticipantKeyEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID participantId;

    @Column(nullable = false)
    private UUID deviceId;

    @Column(nullable = false)
    private Integer keyVersion;

    @Column(nullable = false)
    private String encryptionKey;

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
