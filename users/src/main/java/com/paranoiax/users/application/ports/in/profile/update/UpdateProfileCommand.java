package com.paranoiax.users.application.ports.in.profile.update;

import java.util.UUID;

public record UpdateProfileCommand(
        UUID userId,
        String username,
        String profile
) {
}