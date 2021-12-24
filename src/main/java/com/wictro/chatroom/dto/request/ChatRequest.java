package com.wictro.chatroom.dto.request;

public class ChatRequest {
    private String text;

    public ChatRequest(String text) {
        this.text = text;
    }

    public ChatRequest(){}

    public String getText() {
        return text;
    }
}
