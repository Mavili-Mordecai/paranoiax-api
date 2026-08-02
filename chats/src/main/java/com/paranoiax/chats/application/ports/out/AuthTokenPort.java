package com.paranoiax.chats.application.ports.out;

import com.paranoiax.core.application.AccessToken;

public interface AuthTokenPort {
    AccessToken parseAccessToken(String token);
}