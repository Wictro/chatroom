package com.wictro.chatroom.dto.request;

public class ChatroomUpdateRequest {
    private String displayName;
    private String password;

    public ChatroomUpdateRequest(String displayName, String password) {
        this.displayName = displayName;
        this.password = password;
    }

    public ChatroomUpdateRequest(){}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
