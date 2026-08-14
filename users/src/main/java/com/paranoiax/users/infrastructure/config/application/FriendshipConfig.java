package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.friendship.accept.AcceptFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.block.BlockFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.delete.DeleteFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.get.GetFriendshipsUseCase;
import com.paranoiax.users.application.ports.in.friendship.unblock.UnblockFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.friendship.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class FriendshipConfig {

    @Bean
    public GetFriendshipsUseCase getFriendshipsUseCase(FriendshipPort friendshipPort) {
        return new GetFriendshipsService(friendshipPort);
    }

    @Bean
    public AddFriendshipUseCase addFriendshipUseCase(
            FriendshipPort friendshipPort,
            FriendshipKeyPort friendshipKeyPort,
            DevicePort devicePort,
            UserPort userPort,
            OperationExecutor executor,
            @Value("${application.friendship.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.result-ttl}") Duration resultTtl
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
            @Value("${application.friendship.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.result-ttl}") Duration resultTtl
    ) {
        return new UpdateFriendshipService(friendshipPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public AcceptFriendshipUseCase acceptFriendshipUseCase(
            FriendshipPort friendshipPort,
            OperationExecutor executor,
            @Value("${application.friendship.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.result-ttl}") Duration resultTtl
    ) {
        return new AcceptFriendshipService(friendshipPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public BlockFriendshipUseCase blockFriendshipUseCase(
            FriendshipPort friendshipPort,
            OperationExecutor executor,
            @Value("${application.friendship.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.result-ttl}") Duration resultTtl
    ) {
        return new BlockFriendshipService(friendshipPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public UnblockFriendshipUseCase unblockFriendshipUseCase(
            FriendshipPort friendshipPort,
            OperationExecutor executor,
            @Value("${application.friendship.lock-ttl}") Duration lockTtl,
            @Value("${application.friendship.result-ttl}") Duration resultTtl
    ) {
        return new UnblockFriendshipService(friendshipPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public DeleteFriendshipUseCase deleteFriendshipUseCase(
            FriendshipPort friendshipPort,
            TransactionPort transactionPort
    ) {
        return new DeleteFriendshipService(friendshipPort, transactionPort);
    }
}