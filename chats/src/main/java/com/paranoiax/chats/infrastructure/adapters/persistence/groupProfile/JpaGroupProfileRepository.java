package com.paranoiax.chats.infrastructure.adapters.persistence.groupProfile;

import com.paranoiax.chats.infrastructure.entities.GroupProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGroupProfileRepository extends JpaRepository<GroupProfileEntity, UUID> {
}
