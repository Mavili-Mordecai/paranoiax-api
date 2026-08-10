package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipUseCase;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.friendship.add.AddFriendshipService;
import com.paranoiax.users.application.services.friendship.update.UpdateFriendshipService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class FriendshipConfig {

    @Bean
    public AddFriendshipUseCase addFriendshipUseCase(
            FriendshipPort friendshipPort,
            FriendshipKeyPort friendshipKeyPort,
            DevicePort devicePort,
            UserPort userPort,
            OperationExecutor executor,
            @Value("${application.friendship.add.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.add.resul-ttl}") Duration resultTtl
    ) {
        return new AddFriendshipService(
                friendshipPort,
                friendshipKeyPort,
                devicePort,
                userPort,
                executor,
                lockTtl,
                resultTtl
        );
    }

    @Bean
    public UpdateFriendshipUseCase updateFriendshipUseCase(
            FriendshipPort friendshipPort,
            OperationExecutor executor,
            @Value("${application.friendship.update.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.update.resul-ttl}") Duration resultTtl
    ) {
        return new UpdateFriendshipService(friendshipPort, executor, lockTtl, resultTtl);
    }
}