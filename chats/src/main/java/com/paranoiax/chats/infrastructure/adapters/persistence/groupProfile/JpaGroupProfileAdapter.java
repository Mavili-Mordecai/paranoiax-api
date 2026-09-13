package com.paranoiax.chats.infrastructure.adapters.persistence.groupProfile;

import com.paranoiax.chats.application.ports.out.GroupProfilePort;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfile;
import com.paranoiax.chats.domain.models.groupProfile.GroupProfileId;
import com.paranoiax.chats.infrastructure.entities.GroupProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaGroupProfileAdapter implements GroupProfilePort {
    private final JpaGroupProfileRepository repository;
    private final JpaGroupProfileMapper mapper;

    @Override
    public Optional<GroupProfile> findById(GroupProfileId groupProfileId) {
        return repository.findById(groupProfileId.value()).map(mapper::toDomain);
    }

    @Override
    public List<GroupProfile> findAllById(Collection<GroupProfileId> ids) {
        return repository.findAllById(ids.stream().map(GroupProfileId::value).collect(Collectors.toSet()))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public GroupProfile insert(GroupProfile groupProfile) {
        return mapper.toDomain(repository.save(mapper.toEntity(groupProfile)));
    }

    @Override
    public GroupProfile update(GroupProfile groupProfile) {
        GroupProfileEntity entity = mapper.toEntity(groupProfile);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void delete(GroupProfileId id) {
        repository.deleteById(id.value());
    }
}
