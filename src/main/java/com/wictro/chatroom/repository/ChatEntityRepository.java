package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.ChatEntity;
import com.wictro.chatroom.model.ChatroomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatEntityRepository extends CrudRepository<ChatEntity, Long> {
    void deleteAllByChatroomEntity(ChatroomEntity chatroom);

    List<ChatEntity> findAllByIdGreaterThan(Long lastChatIndex);
    List<ChatEntity> findAllByChatroomEntityAndIdGreaterThan(ChatroomEntity chatroom, Long lastChatIndex);
}
