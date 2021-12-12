package com.wictro.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public String getTest(Model model){
        model.addAttribute("user", "Viktor");
        return "test-templates/test";
    }
}
