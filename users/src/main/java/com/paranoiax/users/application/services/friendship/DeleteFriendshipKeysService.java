package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.application.ports.in.friendship.deleteKeys.DeleteFriendshipKeysCommand;
import com.paranoiax.users.application.ports.in.friendship.deleteKeys.DeleteFriendshipKeysUseCase;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;

public class DeleteFriendshipKeysService implements DeleteFriendshipKeysUseCase {
    private final FriendshipKeyPort friendshipKeyPort;

    public DeleteFriendshipKeysService(FriendshipKeyPort friendshipKeyPort) {
        this.friendshipKeyPort = friendshipKeyPort;
    }

    @Override
    public void execute(DeleteFriendshipKeysCommand command) {
        friendshipKeyPort.deleteAllBy(
                new DeviceId(command.deviceId()),
                command.ids().stream().map(FriendshipKeyId::new).toList()
        );
    }
}