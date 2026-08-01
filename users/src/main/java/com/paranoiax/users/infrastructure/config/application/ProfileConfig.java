package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.profile.search.SearchUserUseCase;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileUseCase;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.profile.SearchUserService;
import com.paranoiax.users.application.services.profile.UpdateProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {

    @Bean
    public UpdateProfileUseCase updateProfileUseCase(UserPort userPort, TransactionPort transactionPort) {
        return new UpdateProfileService(userPort, transactionPort);
    }

    @Bean
    public SearchUserUseCase searchUserUseCase(UserPort userPort) {
        return new SearchUserService(userPort);
    }
}