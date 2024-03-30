package com.c4n.c4n_weather.Users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SignupForm {
    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    public SignupForm(String username, String password, String name, String city, String state) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }
}
