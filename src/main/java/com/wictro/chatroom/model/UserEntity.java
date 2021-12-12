package com.wictro.chatroom.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.*;
import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private java.sql.Time birthday;
    private java.sql.Time createdAt;

    @OneToMany(mappedBy = "chatroomOwner")
    private Set<ChatroomEntity> ownedChatrooms;

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

    public Time getBirthday() {
        return birthday;
    }

    public void setBirthday(Time birthday) {
        this.birthday = birthday;
    }

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time accountSince) {
        this.createdAt = accountSince;
    }
}
