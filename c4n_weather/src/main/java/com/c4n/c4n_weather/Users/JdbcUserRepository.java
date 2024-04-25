package com.c4n.c4n_weather.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcClient jdbcClient;
    private final PasswordEncoder passwordEncoder;

    public JdbcUserRepository(JdbcClient jdbcClient, PasswordEncoder passwordEncoder) {
        this.jdbcClient = jdbcClient;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("SELECT * FROM user WHERE username = :username")
            .param("username", username)
            .query(User.class)
            .optional();
    }

    public void deleteByUsername(String username) {
        var updated = jdbcClient.sql("DELETE FROM user WHERE username = :username")
            .param("username", username)
            .update();
        Assert.state(updated == 1, "Failed to delete user " + username);
    }

    public void deleteAll() {
        jdbcClient.sql("DELETE FROM user")
            .update();
    }

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM user")
            .query(User.class)
            .list();
    }

    public void create(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        jdbcClient.sql("INSERT INTO user (username, password, name) VALUES (:username, :password, :name)")
            .param("username", user.username())
            .param("password", hashedPassword)
            .param("name", user.name())
            .update();
    }

    public void updatePasswordByUsername(String username, String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        var updated = jdbcClient.sql("UPDATE user SET password = :password WHERE username = :username")
            .param("password", hashedPassword)
            .param("username", username)
            .update();
        Assert.state(updated == 1, "Failed to update password for " + username);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM user")
            .query()
            .listOfRows()
            .size();
    }

    public void setCodeByUsername(String username, String code) {
        var updated = jdbcClient.sql("UPDATE user SET code = :code WHERE username = :username")
            .param("code", code)
            .param("username", username)
            .update();
        Assert.state(updated == 1, "Failed to set code for " + username);
    }
}