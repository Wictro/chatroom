package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.ChatroomMembershipEntity;
import com.wictro.chatroom.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatroomMembershipEntityRepository extends CrudRepository<ChatroomMembershipEntity, Long> {
    ChatroomMembershipEntity findChatroomMembershipEntityByChatroomEntityAndUserEntity(ChatroomEntity chatroom, UserEntity user);

    @Query("SELECT u.userEntity from ChatroomMembershipEntity as u where u.chatroomEntity = ?1")
    List<UserEntity> findAllUsersByChatroom(ChatroomEntity chatroom);

    void deleteChatroomMembershipEntityByChatroomEntityAndUserEntity(ChatroomEntity chatroom, UserEntity user);

    void deleteAllByChatroomEntity(ChatroomEntity chatroom);
}
