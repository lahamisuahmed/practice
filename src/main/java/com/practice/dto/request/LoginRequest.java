package com.practice.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull(message = "invalid username")
    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotNull(message = "invalid password")
    @NotBlank(message = "password cannot be empty")
    private String password;

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
}
