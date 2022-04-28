package com.wictro.chatroom.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Date;

public class ChatResponse {
    private ChatSenderReponse sender;
    private String text;
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm:ss z")
    private Timestamp sentTime;

    public ChatResponse(){}

    public ChatResponse(Long id, String text, Date sentTime, Long senderId, String senderName, String senderSurname) {
        this.text = text;
        this.id = id;
        this.sentTime = (Timestamp) sentTime;
        this.sender = new ChatSenderReponse(senderId, senderName, senderSurname);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatSenderReponse getSender() {
        return sender;
    }

    public void setSender(ChatSenderReponse sender) {
        this.sender = sender;
    }

    public ChatResponse(ChatSenderReponse sender, String text, Timestamp sentTime) {
        this.sender = sender;
        this.text = text;
        this.sentTime = sentTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public String getText() {
        return text;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }
}
