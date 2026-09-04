package com.paranoiax.core.domain;

import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

public record EventsSeq(int value) {
    public EventsSeq {
        String fieldName = "events_seq";
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, fieldName);
        if (value < 0) {
            throw new InvalidFormatException(fieldName);
        }
    }

    public static EventsSeq create() {
        return new EventsSeq(0);
    }

    public EventsSeq increment() {
        return new EventsSeq(this.value + 1);
    }

    public boolean greaterThan(EventsSeq other) {
        return this.value > other.value;
    }
}
