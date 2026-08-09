package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipUseCase;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.application.ports.out.FriendshipPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.friendship.add.AddFriendshipService;
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
}