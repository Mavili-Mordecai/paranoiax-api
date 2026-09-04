package com.paranoiax.chats.infrastructure.adapters.persistence.chat;

import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.infrastructure.entities.ChatEntity;
import com.paranoiax.core.domain.users.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaChatAdapter implements ChatPort {
    private final JpaChatMapper mapper;
    private final JpaChatRepository repository;

    @Override
    public Chat insert(Chat chat) {
        return mapper.toDomain(repository.save(mapper.toEntity(chat)));
    }

    @Override
    public Chat update(Chat chat) {
        ChatEntity entity = mapper.toEntity(chat);
        entity.setNew(false);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Chat> findAll(UserId userId) {
        return repository.findByUserId(userId.value())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ChatId chatId) {
        repository.deleteById(chatId.value());
    }
}
