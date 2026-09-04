package com.paranoiax.chats.infrastructure.entities;

import com.paranoiax.chats.domain.models.participant.ParticipantPermission;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats_participants", schema = "chats")
public class ParticipantEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID chatId;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantRole role;

    @Column(nullable = false)
    private boolean isMuted;

    @Column(nullable = false)
    private boolean isPinned;

    @Column(nullable = false)
    private int lastReadSeq;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "permissions", columnDefinition = "VARCHAR(16)[]", nullable = false)
    private Set<ParticipantPermission> permissions;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Instant joinedAt;

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
