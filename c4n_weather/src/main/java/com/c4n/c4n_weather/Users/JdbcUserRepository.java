package com.c4n.c4n_weather.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;

public class JdbcUserRepository implements UserRepository{

    private final JdbcClient jdbcClient;

    public JdbcUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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
        jdbcClient.sql("INSERT INTO user (username, password) VALUES (:username, :password)")
            .param("username", user.username())
            .param("password", user.password())
            .update();
    }

    public void updatePasswordByUsername(String username, String newPassword) {
        var updated = jdbcClient.sql("UPDATE user SET password = :password WHERE username = :username")
            .param("password", newPassword)
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
}
