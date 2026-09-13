package com.paranoiax.chats.infrastructure.adapters.persistence.participant;

import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.infrastructure.entities.ParticipantEntity;
import com.paranoiax.core.domain.users.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaParticipantAdapter implements ParticipantPort {
    private final JpaParticipantRepository repository;
    private final JpaParticipantMapper mapper;

    @Override
    public Participant insert(Participant participant) {
        return mapper.toDomain(repository.save(mapper.toEntity(participant)));
    }

    @Override
    public List<Participant> insertAll(Collection<Participant> participants) {
        return repository.saveAll(participants.stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toList())
                )
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Participant update(Participant participant) {
        ParticipantEntity entity = mapper.toEntity(participant);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Participant> findAll(ChatId chatId) {
        return repository.findAllByChatId(chatId.value())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Participant> findBy(ChatId chatId, UserId userId) {
        return repository.findByChatIdAndUserId(chatId.value(), userId.value()).map(mapper::toDomain);
    }

    @Override
    public void delete(ParticipantId id) {
        repository.deleteById(id.value());
    }
}
