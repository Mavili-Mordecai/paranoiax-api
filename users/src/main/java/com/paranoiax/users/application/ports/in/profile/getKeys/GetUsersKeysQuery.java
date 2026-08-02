package com.paranoiax.users.application.ports.in.profile.getKeys;

import java.util.Set;
import java.util.UUID;

public record GetUsersKeysQuery(Set<UUID> userIds) {
}