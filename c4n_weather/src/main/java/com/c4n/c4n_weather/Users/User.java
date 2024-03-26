package com.c4n.c4n_weather.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record User(
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    String username,
    String password
) {
    
    public User {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
