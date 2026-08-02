package com.paranoiax.users.application.ports.in.profile.getKeys;

import java.util.UUID;

public record GetUserKeysQuery(UUID userId) {
}