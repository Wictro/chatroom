package com.wictro.chatroom.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @GetMapping("")
    public String getChats(@RequestParam(name="code", required = true) String chatroomCode,
                          @RequestParam(name="index", required = false) int lastChatIndex){
        return null;
    }

    @PostMapping("")
    public String postChat(@RequestParam(name="code", required = true) String chatroomCode){
        return null;
    }
}
