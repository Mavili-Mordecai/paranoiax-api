package com.paranoiax.chats.application.ports.out;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;

import java.util.List;

public interface ParticipantPort {
    Participant insert(Participant participant);
    Participant update(Participant participant);
    List<Participant> findAll(ChatId chatId);
    void delete(ParticipantId id);
}
