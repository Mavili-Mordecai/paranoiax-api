package com.paranoiax.users.application.ports.in.auth.register;

public interface RegisterUserUseCase {
    void execute(RegisterUserCommand command);
}
