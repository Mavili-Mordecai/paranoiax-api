package com.paranoiax.chats.application.ports.in.chat.findAll;

import java.util.List;

public interface FindChatsUseCase {
    List<ChatDetails> execute(FindChatsByUserIdQuery query);
}
