package com.wictro.chatroom.controller;

import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.UserEntityRepository;
import com.wictro.chatroom.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DashboardController {
    private final UserEntityRepository userEntityRepository;
    private final AuthService authService;

    public DashboardController(UserEntityRepository userEntityRepository, AuthService authService){
        this.userEntityRepository = userEntityRepository;
        this.authService = authService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpServletRequest request, HttpServletResponse response){
        //get the user from the cookies
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        if(user == null){
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("chatrooms", userEntityRepository.getUserChatrooms(user));
        model.addAttribute("user", user); //1 rresht
        return "dashboard";
    }
}
