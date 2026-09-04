package com.paranoiax.core.domain;

import java.time.Instant;

public interface ActivityTrackable {
    Instant getLastActivityAt();

    void recordActivity(Instant activityTime);
}
