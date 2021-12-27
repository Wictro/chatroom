package com.wictro.chatroom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatroom_id", referencedColumnName = "chatroom_id")
    private ChatroomEntity chatroomEntity;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "user_id")
    private UserEntity sender;

    private java.sql.Timestamp sentDate;

    private String text;

    public ChatEntity(ChatroomEntity chatroomEntity, UserEntity sender, Timestamp sentDate, String text) {
        this.chatroomEntity = chatroomEntity;
        this.sender = sender;
        this.sentDate = sentDate;
        this.text = text;
    }

    public ChatEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatroomEntity getChatroomEntity() {
        return chatroomEntity;
    }

    public void setChatroomEntity(ChatroomEntity chatroomEntity) {
        this.chatroomEntity = chatroomEntity;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
