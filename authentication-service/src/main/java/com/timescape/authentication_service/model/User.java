package com.timescape.authentication_service.model;

import java.util.List;

public class User {
    private String username;
    private List<String> roles;

    public User(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
