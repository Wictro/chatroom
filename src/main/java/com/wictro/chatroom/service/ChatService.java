package com.wictro.chatroom.service;

import com.wictro.chatroom.dto.request.ChatRequest;
import com.wictro.chatroom.dto.response.ChatResponse;
import com.wictro.chatroom.model.ChatEntity;
import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.ChatEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class ChatService {

    private final ChatEntityRepository chatEntityRepository;

    public ChatService(ChatEntityRepository chatEntityRepository) {
        this.chatEntityRepository = chatEntityRepository;
    }

    public void saveChat(ChatroomEntity chatroom, UserEntity user, ChatRequest message){
        chatEntityRepository.save(new ChatEntity(chatroom, user, new java.sql.Timestamp(System.currentTimeMillis()), HtmlUtils.htmlEscape(message.getText())));
    }

    public List<ChatResponse> getNewChats(Long lastChatIndex, ChatroomEntity chatroom){
        return chatEntityRepository.getNewChats(chatroom, lastChatIndex);
    }
}
