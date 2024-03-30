package com.c4n.c4n_weather.Users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

public class LoginForm {
    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    private String password;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
