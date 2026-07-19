package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.ImageUrl;

import java.time.Instant;

public class Avatar {
    private final UserId id;
    private ImageUrl small;
    private ImageUrl medium;
    private ImageUrl large;
    private final Instant createdAt;

    public Avatar(UserId id, ImageUrl small, ImageUrl medium, ImageUrl large, Instant createdAt) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "Id");
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");
    }

    public static Avatar create(UserId id, ImageUrl small, ImageUrl medium, ImageUrl large) {
        return new Avatar(id, small, medium, large, Instant.now());
    }

    public void changeImage(ImageUrl small, ImageUrl medium, ImageUrl large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ImageUrl getLarge() {
        return large;
    }

    public ImageUrl getMedium() {
        return medium;
    }

    public ImageUrl getSmall() {
        return small;
    }

    public UserId getId() {
        return id;
    }
}
