package com.paranoiax.users.domain.models;

import java.time.Instant;

public interface ActivityTrackable {
    Instant getLastSeenAt();

    void recordActivity(Instant activityTime);
}
