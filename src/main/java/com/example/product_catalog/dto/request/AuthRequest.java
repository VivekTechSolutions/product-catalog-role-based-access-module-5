package com.example.product_catalog.dto.request;

import com.example.product_catalog.enums.RoleEnum;

public class AuthRequest {
    private String username;
    private String password;
    private RoleEnum role; 

    // No-args constructor
    public AuthRequest() {
    }

    // All-args constructor
    public AuthRequest(String username, String password, RoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
