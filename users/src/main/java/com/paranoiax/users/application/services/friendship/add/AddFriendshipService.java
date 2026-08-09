package com.paranoiax.users.application.services.friendship.add;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.exceptions.InvalidStateTransitionException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.add.FriendshipKeyInfo;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import com.paranoiax.users.domain.models.friendship.FriendshipStatus;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipSharedKey;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddFriendshipService implements AddFriendshipUseCase {
    private final FriendshipPort friendshipPort;
    private final FriendshipKeyPort friendshipKeyPort;
    private final DevicePort devicePort;
    private final UserPort userPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public AddFriendshipService(
            FriendshipPort friendshipPort,
            FriendshipKeyPort friendshipKeyPort,
            DevicePort devicePort,
            UserPort userPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.friendshipPort = friendshipPort;
        this.friendshipKeyPort = friendshipKeyPort;
        this.devicePort = devicePort;
        this.userPort = userPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(AddFriendshipCommand command) {
        executor.execute(command, String.class, lockTtl, resultTtl, () -> {
            UserId userId = new UserId(command.userId());
            UserId friendId = userPort.findById(new UserId(command.friendId()))
                    .orElseThrow(() -> new NotFoundException("User"))
                    .getId();

            List<DeviceId> deviceIds = command.keys().stream()
                    .map(it -> new DeviceId(it.deviceId()))
                    .toList();

            Map<UUID, DeviceId> devices = devicePort.findAll(deviceIds)
                    .stream()
                    .collect(Collectors.toMap(
                            device -> device.getId().value(), Device::getId,
                            (existing, _) -> existing)
                    );

            if (devices.size() != command.keys().size()) {
                throw new NotFoundException("Devices");
            }

            List<Friendship> friendships = friendshipPort.findBetween(userId, friendId);

            processOutcome(userId, friendId, command.attributes(), friendships);
            Friendship income = processIncome(userId, friendId, friendships);

            if (income == null) {
                return command.operationId();
            }

            processKeys(income, command.keys(), deviceIds, devices);

            return command.operationId();
        });
    }

    private Friendship processOutcome(UserId userId, UserId friendId, String attributes, List<Friendship> friendships) {
        Optional<Friendship> existing = friendships.stream()
                .filter(fs -> fs.getUserId().equals(userId) && fs.getFriendId().equals(friendId))
                .findFirst();

        if (existing.isPresent()) {
            Friendship record = existing.get();

            return switch (record.getStatus()) {
                case DELETED -> {
                    record.resurrect(FriendshipStatus.OUTCOME);
                    record.changeAttributes(attributes);
                    yield friendshipPort.update(record);
                }
                case OUTCOME, ACCEPTED -> record;
                default -> throw new InvalidStateTransitionException(
                        record.getStatus().name(),
                        FriendshipStatus.OUTCOME.name()
                );
            };
        }

        return friendshipPort.insert(Friendship.outcome(userId, friendId, new FriendshipAttributes(attributes)));
    }

    private Friendship processIncome(
            UserId userId,
            UserId friendId,
            List<Friendship> friendships
    ) {
        Optional<Friendship> existing = friendships.stream()
                .filter(fs -> fs.getUserId().equals(friendId) && fs.getFriendId().equals(userId))
                .findFirst();

        if (existing.isPresent()) {
            Friendship record = existing.get();
            return switch (record.getStatus()) {
                case DELETED -> {
                    record.resurrect(FriendshipStatus.INCOME);
                    yield friendshipPort.update(record);
                }
                case INCOME, ACCEPTED -> record;
                case BLOCKED -> null;
                default -> throw new InvalidStateTransitionException(
                        record.getStatus().name(),
                        FriendshipStatus.INCOME.name()
                );
            };
        }

        return friendshipPort.insert(Friendship.income(friendId, userId));
    }

    private void processKeys(
            Friendship friendship,
            List<FriendshipKeyInfo> commandKeys,
            List<DeviceId> deviceIds,
            Map<UUID, DeviceId> devices
    ) {
        List<FriendshipKey> newKeys = new ArrayList<>();
        List<FriendshipKey> updatedKeys = new ArrayList<>();

        Map<UUID, FriendshipKey> existingKeys = friendshipKeyPort.findExistingKeys(friendship.getId(), deviceIds)
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getFriendDeviceId().value(),
                        Function.identity(),
                        (old, _) -> old
                ));

        for (FriendshipKeyInfo key : commandKeys) {
            FriendshipKey existingKey = existingKeys.get(key.deviceId());

            if (existingKey == null) {
                newKeys.add(FriendshipKey.create(
                        friendship.getId(),
                        devices.get(key.deviceId()),
                        new FriendshipSharedKey(key.sharedKey())
                ));
                continue;
            }

            FriendshipSharedKey newSharedKey = new FriendshipSharedKey(key.sharedKey());
            if (existingKey.hasSameKey(newSharedKey)) {
                continue;
            }

            existingKey.changeSharedKey(newSharedKey);
            updatedKeys.add(existingKey);
        }

        if (!updatedKeys.isEmpty()) {
            friendshipKeyPort.saveAll(updatedKeys);
        }

        if (!newKeys.isEmpty()) {
            friendshipKeyPort.insertAll(newKeys);
        }
    }
}