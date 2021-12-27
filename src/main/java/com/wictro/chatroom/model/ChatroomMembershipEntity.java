package com.wictro.chatroom.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ChatroomMembershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", referencedColumnName = "chatroom_id")
    private ChatroomEntity chatroomEntity;

    private Timestamp joinedSince;

    public ChatroomMembershipEntity(){}

    public ChatroomMembershipEntity(UserEntity userEntity, ChatroomEntity chatroomEntity){
        this.userEntity = userEntity;
        this.chatroomEntity = chatroomEntity;
        this.joinedSince = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ChatroomEntity getChatroomEntity() {
        return chatroomEntity;
    }

    public void setChatroomEntity(ChatroomEntity chatroomEntity) {
        this.chatroomEntity = chatroomEntity;
    }

    public Timestamp getJoinedSince() {
        return joinedSince;
    }

    public void setJoinedSince(Timestamp joinedSince) {
        this.joinedSince = joinedSince;
    }
}
