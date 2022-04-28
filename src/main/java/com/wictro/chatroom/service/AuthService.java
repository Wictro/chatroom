package com.wictro.chatroom.service;

import com.wictro.chatroom.model.SessionEntity;
import com.wictro.chatroom.model.UserEntity;
import com.wictro.chatroom.repository.SessionEntityRepository;
import com.wictro.chatroom.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserEntityRepository userEntityRepository;
    private final SessionEntityRepository sessionEntityRepository;

    private final int EXPIRE_AFTER = 12000000;

    public AuthService(UserEntityRepository userRepository, SessionEntityRepository sessionRepository){
        this.userEntityRepository = userRepository;
        this.sessionEntityRepository = sessionRepository;
    }

    public SessionEntity getSession(Cookie[] cookies){
        Optional<Cookie> sessionCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("sid")).findFirst();

        if(!sessionCookie.isPresent())
            return null;

        UUID uuid = null;

        try {
            uuid = UUID.fromString(sessionCookie.get().getValue());
        }
        catch (Exception e){}

        if(uuid == null){
            return null;
        }

        Optional<SessionEntity> session = sessionEntityRepository.findById(uuid);

        return session.get();
    }

    public UserEntity getLoggedInUser(Cookie[] cookies){
        if(cookies == null)
            return null;

        //check if it has the desired cookies and check if the session exists
        Optional<Cookie> loggedInCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("logged_in") && c.getValue().equals("true")).findFirst();
        if(!loggedInCookie.isPresent())
            return null;

        Optional<Cookie> sessionCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("sid")).findFirst();
        if(!sessionCookie.isPresent())
            return null;

        UUID uuid = null;

        try {
            uuid = UUID.fromString(sessionCookie.get().getValue());
        }
        catch (Exception e){}

        if(uuid == null){
            return null;
        }

        Optional<SessionEntity> session = sessionEntityRepository.findById(uuid);

        if(!session.isPresent())
            return null;

        java.sql.Timestamp expire_date = session.get().getExpireDate();

        if(expire_date.before(new Date(System.currentTimeMillis()))){
            sessionEntityRepository.delete(session.get());
            return null;
        }

        UserEntity loggedInUser = session.get().getUser();

        return loggedInUser;
    }

    public boolean userIsLoggedIn(Cookie[] cookies) {
        if(cookies == null)
            return false;

        //check if it has the desired cookies and check if the session exists
        Optional<Cookie> loggedInCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("logged_in") && c.getValue().equals("true")).findFirst();
        if(!loggedInCookie.isPresent())
            return false;

        Optional<Cookie> sessionCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("sid")).findFirst();
        if(!sessionCookie.isPresent())
            return false;

        UUID uuid = null;

        try {
            uuid = UUID.fromString(sessionCookie.get().getValue());
        }
        catch (Exception e){}

        if(uuid == null){
            return false;
        }

        Optional<SessionEntity> session = sessionEntityRepository.findById(uuid);

        if(!session.isPresent())
            return false;

        java.sql.Timestamp expire_date = session.get().getExpireDate();

        if(expire_date.before(new Date(System.currentTimeMillis()))){
            sessionEntityRepository.delete(session.get());
            return false;
        }

        UserEntity loggedInUser = session.get().getUser();

        if(loggedInUser == null)
            return false;

        return true;
    }

    public boolean userExists(String email){
        return userEntityRepository.findByEmail(email) != null;
    }

    public void invalidateClientSessionCookies(HttpServletResponse response){
        Cookie loggedInCookie = new Cookie("logged_in", "false");
        loggedInCookie.setMaxAge(0);
        loggedInCookie.setPath("/");
        Cookie sidCookie = new Cookie("sid", "");
        sidCookie.setMaxAge(0);
        sidCookie.setPath("/");

        response.addCookie(loggedInCookie);
        response.addCookie(sidCookie);
    }

    public UUID saveSessionInDatabase(UserEntity loggedInUser, HttpServletRequest request){
        SessionEntity session = new SessionEntity();
        session.setExpireDate(new java.sql.Timestamp(System.currentTimeMillis() + this.EXPIRE_AFTER));
        session.setUser(loggedInUser);
        session.setIpAddress(request.getRemoteAddr());
        UUID uuid = UUID.randomUUID();
        session.setId(uuid);
        sessionEntityRepository.save(session);
        return uuid;
    }

    public void setClientSessionCookies(HttpServletResponse response, UUID uuid){
        Cookie sessionCookie = new Cookie("sid", uuid.toString());
        sessionCookie.setMaxAge(this.EXPIRE_AFTER/1000);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);

        Cookie loggedInCookie = new Cookie("logged_in", "true");
        loggedInCookie.setMaxAge(this.EXPIRE_AFTER/1000);
        loggedInCookie.setPath("/");
        response.addCookie(loggedInCookie);
    }
}
