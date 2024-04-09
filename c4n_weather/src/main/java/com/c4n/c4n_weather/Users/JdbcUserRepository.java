package com.c4n.c4n_weather.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcClient jdbcClient;
    //private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        //come back and fix this later
        //String hashedpassword = passwordEncoder.encode(user.password());
        jdbcClient.sql("INSERT INTO user (username, password, name) VALUES (:username, :password, :name)")
            .param("username", user.username())
            .param("password", user.password())
            .param("name", user.name())
            .update();
    }

    public void updatePasswordByUsername(String username, String newPassword) {
        //come back and fix this later
        //String hashedpassword = passwordEncoder.encode(newPassword);
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