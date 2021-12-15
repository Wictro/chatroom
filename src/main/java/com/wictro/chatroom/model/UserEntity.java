package com.wictro.chatroom.model;

import javax.persistence.*;
import java.sql.*;
import java.util.Date;
import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt;

    @OneToMany(mappedBy = "chatroomOwner")
    private Set<ChatroomEntity> ownedChatrooms;

    @OneToMany(mappedBy = "user")
    private Set<SessionEntity> userSessions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time accountSince) {
        this.createdAt = accountSince;
    }
}
