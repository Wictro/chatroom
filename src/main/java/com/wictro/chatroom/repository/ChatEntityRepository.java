package com.wictro.chatroom.repository;

import com.wictro.chatroom.dto.response.ChatResponse;
import com.wictro.chatroom.model.ChatEntity;
import com.wictro.chatroom.model.ChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatEntityRepository extends JpaRepository<ChatEntity, Long> {
    void deleteAllByChatroomEntity(ChatroomEntity chatroom);

    @Query("SELECT new com.wictro.chatroom.dto.response.ChatResponse(u.id, u.text, u.sentTime, v.id, v.firstName, v.lastName) from ChatEntity as u join u.sender v WHERE u.chatroomEntity = ?1 AND u.id > ?2")
    List<ChatResponse> getNewChats(ChatroomEntity chatroom, Long lastChatIndex);
}
