package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.ChatroomEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatroomEntityRepository extends CrudRepository<ChatroomEntity, Long> {
    ChatroomEntity findChatroomEntityByChatroomCode(String chatroomCode);
}
