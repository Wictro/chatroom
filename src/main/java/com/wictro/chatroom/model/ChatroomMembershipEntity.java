package com.wictro.chatroom.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class ChatroomMembershipEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", referencedColumnName = "chatroom_id")
    private ChatroomEntity chatroomEntity;

    private java.sql.Time joinedSince;

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

    public Time getJoinedSince() {
        return joinedSince;
    }

    public void setJoinedSince(Time joinedSince) {
        this.joinedSince = joinedSince;
    }
}
