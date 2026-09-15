package com.paranoiax.chats.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats_invites", schema = "chats")
public class InviteEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID chatId;

    @Column(nullable = false)
    private UUID createdById;

    @Column(nullable = false)
    private Integer usesCount;

    @Column
    private Integer maxUses;

    @Column
    private Instant expiresAt;

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
