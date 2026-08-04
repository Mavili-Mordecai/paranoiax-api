package com.paranoiax.users.application.services.profile;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileCommand;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileUseCase;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.core.domain.exceptions.AlreadyTakenException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

public class UpdateProfileService implements UpdateProfileUseCase {
    private final UserPort userPort;
    private final TransactionPort transactionPort;

    public UpdateProfileService(UserPort userPort, TransactionPort transactionPort) {
        this.userPort = userPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(UpdateProfileCommand command) {
        transactionPort.execute(() -> {
            User user = userPort.findById(new UserId(command.userId()))
                    .orElseThrow(() -> new NotFoundException("User"));

            if (command.username() != null && !command.username().isBlank()) {
                Username newUsername = new Username(command.username());

                if (!newUsername.equals(user.getUsername())) {
                    if (userPort.findByUsername(newUsername).isPresent()) {
                        throw new AlreadyTakenException("Username");
                    }
                    user.changeUsername(newUsername);
                }
            }

            if (command.profile() != null) {
                user.changeProfile(command.profile().isBlank() ? null : command.profile());
            }

            return userPort.update(user);
        });
    }
}