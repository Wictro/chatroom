package com.wictro.chatroom.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class ChatroomEntity {
    @Column(name = "chatroom_id")
    @Id
    private Long id;

    private String chatroomCode;
    private java.sql.Time createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private UserEntity chatroomOwner;

    private String chatroomDisplayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatroomCode() {
        return chatroomCode;
    }

    public void setChatroomCode(String chatroomCode) {
        this.chatroomCode = chatroomCode;
    }

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getChatroomOwner() {
        return chatroomOwner;
    }

    public void setChatroomOwner(UserEntity chatroomOwner) {
        this.chatroomOwner = chatroomOwner;
    }

    public String getChatroomDisplayName() {
        return chatroomDisplayName;
    }

    public void setChatroomDisplayName(String chatroomDisplayName) {
        this.chatroomDisplayName = chatroomDisplayName;
    }
}
