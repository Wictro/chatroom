package com.wictro.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLogin(){
        return null;
    }

    @GetMapping("/signup")
    public String getSignup(){
        return null;
    }

    @PostMapping("/login")
    public String postLogin(){
        return null;
    }

    @PostMapping("/signup")
    public String postSignup(){
        return null;
    }
}
