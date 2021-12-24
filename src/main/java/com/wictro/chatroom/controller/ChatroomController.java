package com.wictro.chatroom.controller;

import com.wictro.chatroom.dto.request.ChatroomUpdateRequest;
import com.wictro.chatroom.model.ChatroomEntity;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.ChatroomEntityRepository;
import com.wictro.chatroom.service.AuthService;
import com.wictro.chatroom.service.ChatroomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {
    private final AuthService authService;
    private final ChatroomService chatroomService;

    public ChatroomController(AuthService authService, ChatroomService chatroomService) {
        this.authService = authService;
        this.chatroomService = chatroomService;
    }

    //returns a chatroom template
    @GetMapping("/{code}")
    public String getChatroom(@PathVariable(required = false) String code, HttpServletRequest request, HttpServletResponse response, Model model){
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        if(code == null){
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        //check if the user is a member of the chatroom, if yes then put the desired chatroom in the model
        ChatroomEntity chatroom = chatroomService.getChatroomFromCodeIfUserIsMember(user, code);

        if(chatroom == null){
            return "error-templates/unauthorized";
        }

        model.addAttribute("chatroom", chatroom);

        List<UserEntity> members = chatroomService.getChatroomMembers(chatroom);

        model.addAttribute("members", members);

        boolean owner = false;
        if(chatroom.getChatroomOwner().equals(user))
            owner = true;

        model.addAttribute("owner", owner);

        return "chatroom-templates/chatroom";
    }

    //creates a new chatroom with data in the payload
    @PostMapping("")
    public String createChatroom(@ModelAttribute ChatroomEntity chatroomEntity, HttpServletRequest request, HttpServletResponse response){
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            return "error-templates/unauthorized";
        }

        //validate name...

        chatroomService.createChatroom(user, chatroomEntity.getDisplayName());

        try {
            response.sendRedirect("/dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //delete a chatroom
    @DeleteMapping("")
    public String deleteChatroom(){
        return null;
    }

    //update a chatroom
    @PutMapping("/{code}")
    public String updateChatroom(@PathVariable("code") String code, @RequestBody ChatroomUpdateRequest newChatroom,
                                 HttpServletRequest request, HttpServletResponse response){
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        if(code == null){
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        //check if the user is a member of the chatroom, if yes then put the desired chatroom in the model
        ChatroomEntity chatroom = chatroomService.getChatroomFromCodeIfUserIsMember(user, code);

        if(chatroom == null){
            return "error-templates/unauthorized";
        }

        //we must later add admins, but at the moment only the owner can change the settings

        if(!chatroom.getChatroomOwner().equals(user)) {
            return "error-templates/unauthorized";
        }

        chatroom.setDisplayName(newChatroom.getDisplayName());
        chatroom.setPassword(newChatroom.getPassword());

        chatroomService.updateChatroom(chatroom);

        return null;
    }

    //get all the chatroom users
    @GetMapping("/users")
    public String getChatroomUsers(){
        return null;
    }

    //add a user to a chatroom - if admin can do this
    @PostMapping("/users")
    public String addUserToChatroom(@ModelAttribute UserEntity userEntity){
        return null;
    }

    @DeleteMapping("/users")
    public String removeUserFromChatroom(){
        return null;
    }

    @PostMapping("/join")
    public String joinChatroom(HttpServletRequest request, @ModelAttribute ChatroomEntity chatroomEntity, HttpServletResponse response){
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            return "error-templates/unauthorized";
        }

        boolean success = chatroomService.saveUserToChatroom(user, chatroomEntity.getChatroomCode());

        if(!success)
            return "error-templates/wrong";

        try {
            response.sendRedirect("/dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/{code}/leave")
    public String leaveChatroom(HttpServletRequest request, @PathVariable("code") String code,
                                HttpServletResponse response){
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        if(code == null){
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        //check if the user is a member of the chatroom, if yes then put the desired chatroom in the model
        ChatroomEntity chatroom = chatroomService.getChatroomFromCodeIfUserIsMember(user, code);

        if(chatroom == null){
            return "error-templates/unauthorized";
        }

        chatroomService.leaveChatroom(user, chatroom);

        try {
            response.sendRedirect("/dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
