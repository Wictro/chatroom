package com.wictro.chatroom.service;

import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.ChatroomMembershipEntity;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.ChatroomEntityRepository;
import com.wictro.chatroom.repository.ChatroomMembershipEntityRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class ChatroomService {
    private final ChatroomMembershipEntityRepository chatroomMembershipEntityRepository;
    private final ChatroomEntityRepository chatroomEntityRepository;

    public ChatroomEntity getChatroomFromCodeIfUserIsMember(UserEntity user, String chatroomCode){
        ChatroomEntity chatroom = chatroomEntityRepository.findChatroomEntityByChatroomCode(chatroomCode);

        if(this.isMember(user, chatroom)){
            return chatroom;
        }

        return null;
    }

    public List<UserEntity> getChatroomMembers(ChatroomEntity chatroom){
        return chatroomMembershipEntityRepository.findAllUsersByChatroom(chatroom);
    }

    public void updateChatroom(ChatroomEntity chatroom){
        chatroomEntityRepository.save(chatroom);
    }

    @Transactional
    public void leaveChatroom(UserEntity user, ChatroomEntity chatroom){
        chatroomMembershipEntityRepository.deleteChatroomMembershipEntityByChatroomEntityAndUserEntity(chatroom, user);
    }

    public boolean isMember(UserEntity user, ChatroomEntity chatroom) {
        return chatroomMembershipEntityRepository.findChatroomMembershipEntityByChatroomEntityAndUserEntity(chatroom, user) != null;
    }

    public ChatroomService(ChatroomMembershipEntityRepository chatroomMembershipEntityRepository, ChatroomEntityRepository chatroomEntityRepository) {
        this.chatroomMembershipEntityRepository = chatroomMembershipEntityRepository;
        this.chatroomEntityRepository = chatroomEntityRepository;
    }

    public void createChatroom(UserEntity userEntity, String chatroomName){
        ChatroomEntity chatroom = new ChatroomEntity(userEntity, chatroomName, this.getRandomCode());
        chatroomEntityRepository.save(chatroom);

        ChatroomMembershipEntity membership = new ChatroomMembershipEntity(userEntity, chatroom);
        chatroomMembershipEntityRepository.save(membership);
    }

    public boolean saveUserToChatroom(UserEntity user, String chatroomCode){
        ChatroomEntity chatroom = chatroomEntityRepository.findChatroomEntityByChatroomCode(chatroomCode);

        if(chatroom == null)
            return false;


        if(chatroomMembershipEntityRepository.findChatroomMembershipEntityByChatroomEntityAndUserEntity(chatroom, user) != null)
            return false;

        ChatroomMembershipEntity membership = new ChatroomMembershipEntity(user, chatroom);

        chatroomMembershipEntityRepository.save(membership);

        return true;
    }

    public String getRandomCode(){
        int leftLimit = 65;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> i <= 90 || i >= 97)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
