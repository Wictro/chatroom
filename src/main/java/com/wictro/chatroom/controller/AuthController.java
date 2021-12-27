package com.wictro.chatroom.controller;

import com.wictro.chatroom.model.LoginModel;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.SessionEntityRepository;
import com.wictro.chatroom.repository.UserEntityRepository;
import com.wictro.chatroom.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserEntityRepository userEntityRepository;
    private final SessionEntityRepository sessionEntityRepository;
    private final AuthService authService;

    public AuthController(UserEntityRepository userEntityRepository, AuthService authService, SessionEntityRepository sessionEntityRepository){
        this.userEntityRepository = userEntityRepository;
        this.authService = authService;
        this.sessionEntityRepository = sessionEntityRepository;
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "nomatch", required = false) boolean nomatch,
                           @RequestParam(name="success", required = false) boolean success){
        //if the user is logged in, redirect them to the dashboard page
        if(authService.userIsLoggedIn(request.getCookies())){
            try {
                response.sendRedirect("/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        authService.invalidateClientSessionCookies(response);

        model.addAttribute("loginModel", new LoginModel());

        if(nomatch)
            model.addAttribute("nomatch", "that username and password don't match");
        else if(success)
            model.addAttribute("success", "account created successfully");

        return "auth-templates/login";
    }

    @GetMapping("/signup")
    public String getSignup(Model model, @RequestParam(name = "exists", required = false) boolean exists){
        model.addAttribute("user", new UserEntity());
        if(exists)
            model.addAttribute("exists", "that account already exists");
        return "auth-templates/signup";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute LoginModel userInfo, HttpServletResponse response, HttpServletRequest request){
        //validate input

        UserEntity loggedInUser = userEntityRepository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());

        if(loggedInUser == null){
            try {
                response.sendRedirect("/auth/login?nomatch=true");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        UUID uuid = authService.saveSessionInDatabase(loggedInUser, request);
        authService.setClientSessionCookies(response, uuid);

        try {
            response.sendRedirect("/dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Saves a user to the database in a simple manner - no validations
    //@TODO - include birthday and createdAt
    @PostMapping("/signup")
    public String postSignup(@ModelAttribute UserEntity user, HttpServletResponse response, Model model) throws IOException {
        //validate input

        //if the user exists, tell them
        if(authService.userExists(user.getEmail())){
            model.addAttribute("user", new UserEntity());

            response.sendRedirect("/auth/signup?exists=true");

            return null;
        }

        user.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        //save user
        userEntityRepository.save(user);

        //redirect to login
        response.sendRedirect("/auth/login?success=true");

        return null;
    }

    @GetMapping("/signout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "sid", required = false) String sid){
        UserEntity loggedInUser = authService.getLoggedInUser(request.getCookies());

        if(loggedInUser != null){
            if(sid != null) {
                UUID uuid = null;

                try {
                    uuid = UUID.fromString(sid);
                } catch (Exception e) {
                }

                if (uuid == null)
                    return null;

                sessionEntityRepository.deleteById(uuid);

                try {
                    response.sendRedirect("/auth/dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            authService.invalidateClientSessionCookies(response);
            sessionEntityRepository.delete(authService.getSession(request.getCookies()));
        }

        try {
            response.sendRedirect("/auth/login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
