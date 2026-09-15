package com.paranoiax.chats.infrastructure.adapters.persistence.invite;

import com.paranoiax.chats.application.ports.out.InvitePort;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteId;
import com.paranoiax.chats.infrastructure.entities.InviteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaInviteAdapter implements InvitePort {
    private final JpaInviteRepository repository;
    private final JpaInviteMapper mapper;

    @Override
    public Optional<Invite> findById(InviteId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Invite> findAll(ChatId chatId) {
        return repository.findAllByChatId(chatId.value())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Invite insert(Invite invite) {
        return mapper.toDomain(repository.save(mapper.toEntity(invite)));
    }

    @Override
    public Invite update(Invite invite) {
        InviteEntity entity = mapper.toEntity(invite);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(InviteId id) {
        repository.deleteById(id.value());
    }
}
