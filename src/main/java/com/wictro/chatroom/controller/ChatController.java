package com.wictro.chatroom.controller;

import com.wictro.chatroom.dto.request.ChatRequest;
import com.wictro.chatroom.model.ChatEntity;
import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.ChatroomEntityRepository;
import com.wictro.chatroom.service.AuthService;
import com.wictro.chatroom.service.ChatService;
import com.wictro.chatroom.service.ChatroomService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chatroom/{code}")
public class ChatController {
    private final AuthService authService;
    private final ChatroomEntityRepository chatroomEntityRepository;
    private final ChatroomService chatroomService;
    private final ChatService chatService;

    public ChatController(AuthService authService, ChatroomEntityRepository chatroomEntityRepository,
                          ChatroomService chatroomService,
                          ChatService chatService) {
        this.authService = authService;
        this.chatroomEntityRepository = chatroomEntityRepository;
        this.chatroomService = chatroomService;
        this.chatService = chatService;
    }


    @GetMapping("/chat")
    public List<ChatEntity> getChats(@PathVariable("code") String code,
                                     @RequestParam(name="index", required = false) Long lastChatIndex,
                                     HttpServletRequest request){

        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null)
            return null;

        ChatroomEntity chatroom = chatroomEntityRepository.findChatroomEntityByChatroomCode(code);

        if(chatroom == null)
            return null;

        if(!chatroomService.isMember(user, chatroom))
            return null;

        if(lastChatIndex == null)
            return chatroom.getChats();
        else{
            return chatService.getNewChats(lastChatIndex, chatroom);
        }
    }

    @PostMapping("/chat")
    public void postChat(@PathVariable("code") String code,
                           HttpServletRequest request,
                           @RequestBody ChatRequest message){

        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null)
            return;

        ChatroomEntity chatroom = chatroomEntityRepository.findChatroomEntityByChatroomCode(code);

        if(chatroom == null)
            return;

        if(!chatroomService.isMember(user, chatroom))
            return;

        //save chat to database
        chatService.saveChat(chatroom, user, message);
    }
}
