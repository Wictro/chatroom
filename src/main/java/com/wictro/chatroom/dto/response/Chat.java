package com.wictro.chatroom.dto.response;

import java.time.LocalDateTime;

public class Chat {
    private String sender;
    private String text;
    private LocalDateTime time;

    public Chat(String sender, String text, LocalDateTime time) {
        this.sender = sender;
        this.text = text;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
