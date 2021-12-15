package com.wictro.chatroom.repository;

import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);

    @Query("SELECT c.chatroomEntity from ChatroomMembershipEntity c where c.userEntity = ?1")
    ArrayList<ChatroomEntity> getUserChatrooms(UserEntity user);
}
