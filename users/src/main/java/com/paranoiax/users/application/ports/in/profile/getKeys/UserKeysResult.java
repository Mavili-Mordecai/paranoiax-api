package com.paranoiax.users.application.ports.in.profile.getKeys;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.user.User;

import java.util.List;

public record UserKeysResult(
        UserId userId,
        IdentityKey identityKey,
        List<UserDeviceInfo> devices
) {
    public static UserKeysResult from(User user, List<Device> device) {
        return new UserKeysResult(
                user.getId(),
                user.getIdentityKey(),
                device.stream().map(UserDeviceInfo::from).toList()
        );
    }
}