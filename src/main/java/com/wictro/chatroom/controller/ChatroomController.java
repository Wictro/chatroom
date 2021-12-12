package com.wictro.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {
    //returns a chatroom template
    @GetMapping("")
    public String getChatroom(@RequestParam(name = "code", required = true) String code){
        return null;
    }

    //creates a new chatroom with data in the payload
    @PostMapping("")
    public String createChatroom(){
        return null;
    }

    @DeleteMapping("")
    public String deleteChatroom(){
        return null;
    }

    @PutMapping("")
    public String updateChatroom(){
        return null;
    }

    @GetMapping("/users")
    public String getChatroomUsers(){
        return null;
    }

    @PostMapping("/users")
    public String addUserToChatroom(){
        return null;
    }

    @DeleteMapping("/users")
    public String removeUserfromChatroom(){
        return null;
    }
}
