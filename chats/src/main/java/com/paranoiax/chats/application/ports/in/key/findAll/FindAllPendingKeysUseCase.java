package com.paranoiax.chats.application.ports.in.key.findAll;

import java.util.List;

public interface FindAllPendingKeysUseCase {
    List<PendingKeyDetails> execute(FindAllPendingKeysQuery query);
}
