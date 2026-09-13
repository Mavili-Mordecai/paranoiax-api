package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.core.domain.users.UserId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ParticipantPort {
    Participant insert(Participant participant);
    List<Participant> insertAll(Collection<Participant> participants);
    Participant update(Participant participant);
    List<Participant> findAll(ChatId chatId);
    Optional<Participant> findBy(ChatId chatId, UserId userId);
    void delete(ParticipantId id);
}
