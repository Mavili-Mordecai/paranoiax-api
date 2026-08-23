package com.paranoiax.chats.domain.models.chat;

import com.paranoiax.core.domain.ActivityTrackable;
import com.paranoiax.core.domain.EventsSeq;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.time.Instant;

public class Chat implements ActivityTrackable {
    private final ChatId id;
    private final ChatType type;
    private EventsSeq eventsSeq;
    private Instant lastActivityAt;
    private final Instant createdAt;

    private Chat(ChatId id, ChatType type, EventsSeq eventsSeq, Instant lastActivityAt, Instant createdAt) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.type = Require.notNull(type, DomainErrorCode.MISSING_REQUIRED_FIELD, "type");
        this.eventsSeq = Require.notNull(eventsSeq, DomainErrorCode.MISSING_REQUIRED_FIELD, "eventsSeq");
        this.lastActivityAt = Require.notNull(lastActivityAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "lastActivityAt");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdAt");
    }

    public static Chat of(ChatId id, ChatType type, EventsSeq eventsSeq, Instant lastActivityAt, Instant createdAt) {
        return new Chat(id, type, eventsSeq, lastActivityAt, createdAt);
    }

    public static Chat create(ChatType type) {
        Instant now = Instant.now();
        return new Chat(ChatId.create(), type, EventsSeq.create(), now, now);
    }

    @Override
    public void recordActivity(Instant activityTime) {
        Require.notNull(activityTime, DomainErrorCode.MISSING_REQUIRED_FIELD, "activityTime");

        if (this.lastActivityAt != null && activityTime.isBefore(this.lastActivityAt)) {
            return;
        }

        this.lastActivityAt = Require.after(activityTime, "activityTime", this.createdAt, "createdAt");
    }

    public void advanceEventsSeq() {
        this.eventsSeq = this.eventsSeq.increment();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getLastActivityAt() {
        return lastActivityAt;
    }

    public EventsSeq getEventsSeq() {
        return eventsSeq;
    }

    public ChatType getType() {
        return type;
    }

    public ChatId getId() {
        return id;
    }
}
