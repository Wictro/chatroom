package com.wictro.chatroom.model;

import javax.persistence.*;

@Entity
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    Long id;

    @ManyToOne
    @JoinColumn(name="chatroom_id", referencedColumnName = "chatroom_id")
    private ChatroomEntity chatroomEntity;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "user_id")
    private UserEntity sender;

    private java.sql.Time sentDate;

    private String text;


}
