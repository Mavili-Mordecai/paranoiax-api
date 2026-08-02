package com.paranoiax.users.application.services.profile;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.profile.getKeys.*;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.core.domain.exceptions.InvalidLengthException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.user.User;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetUserKeysService implements GetUserKeysUseCase {
    private final UserPort userPort;
    private final DevicePort devicePort;
    private final TransactionPort transactionPort;
    private final int maxUserIdsPerBatch;

    public GetUserKeysService(UserPort userPort, DevicePort devicePort, TransactionPort transactionPort, int maxUserIdsPerBatch) {
        this.userPort = userPort;
        this.devicePort = devicePort;
        this.transactionPort = transactionPort;
        this.maxUserIdsPerBatch = maxUserIdsPerBatch;
    }

    @Override
    public UserKeysResult execute(GetUserKeysQuery query) {
        return transactionPort.execute(() -> {
            User user = userPort.findById(new UserId(query.userId()))
                    .orElseThrow(() -> new NotFoundException("User"));

            List<Device> devices = devicePort.findByUserId(user.getId());

            if (devices.isEmpty()) {
                throw new NotFoundException("Devices");
            }

            return UserKeysResult.from(user, devices);
        });
    }

    @Override
    public List<UserKeysResult> execute(GetUsersKeysQuery query) {
        return transactionPort.execute(() -> {
            if (query.userIds().size() > maxUserIdsPerBatch) {
                throw new InvalidLengthException("userIds size", 1, maxUserIdsPerBatch);
            }

            Map<UserId, User> usersIdentityKeys = userPort.findByIdIn(query.userIds())
                    .stream()
                    .collect(Collectors.toMap(User::getId, Function.identity(), (existing, replacement) -> existing));

            if (usersIdentityKeys.isEmpty()) {
                return List.of();
            }

            return devicePort.findByUserIdIn(usersIdentityKeys.keySet())
                    .stream()
                    .collect(Collectors.groupingBy(Device::getUserId))
                    .entrySet()
                    .stream()
                    .map(entry -> UserKeysResult.from(usersIdentityKeys.get(entry.getKey()), entry.getValue()))
                    .toList();
        });
    }
}