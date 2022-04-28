package com.wictro.chatroom.controller;

import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DefaultController {

    private final AuthService authService;

    public DefaultController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String deafultRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserEntity user = authService.getLoggedInUser(request.getCookies());

        //if logged in redirect to dashboard
        if(user == null){
            try {
                response.sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //else redirect to login
        else{
            response.sendRedirect("/dashboard");
        }

        return null;
    }
}
