package com.example.infra42.oauth2;


import com.example.infra42.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;

    public SessionUser(User user) {
        this.name = user.getName();
    }
}
