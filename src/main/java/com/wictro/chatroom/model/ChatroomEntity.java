package com.wictro.chatroom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatroomEntity {
    @Column(name = "chatroom_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String chatroomCode;
    private java.sql.Time createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private UserEntity chatroomOwner;

    @JsonIgnore
    @OneToMany(mappedBy = "chatroomEntity")
    private List<ChatEntity> chats;

    private String displayName;

    @JsonIgnore
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ChatEntity> getChats() {
        return chats;
    }

    public ChatroomEntity(UserEntity userEntity, String displayName, String chatroomCode, String chatroomPassword){
        this.setDisplayName(displayName);
        this.setChatroomCode(chatroomCode);
        this.setChatroomOwner(userEntity);
        this.setPassword(chatroomPassword);
    }

    public ChatroomEntity(){}

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String chatroomDisplayName) {
        this.displayName = chatroomDisplayName;
    }
}
