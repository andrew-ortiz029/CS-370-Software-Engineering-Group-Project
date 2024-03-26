package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;

public class JdbcLocationRepository implements LocationRepository{

    private final JdbcClient jdbcClient;

    public JdbcLocationRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Location> findByUser(String user) {
        return jdbcClient.sql("SELECT * FROM location WHERE user = :user")
            .param("user", user)
            .query(Location.class)
            .list();
    }

    public void deleteByUser(String user) {
        var updated = jdbcClient.sql("DELETE FROM location WHERE user = :user")
            .param("user", user)
            .update();
        Assert.state(updated == 1, "Failed to delete location from " + user);
    }

    public Optional<Location> findByUserHome(String user, boolean home) {
        return jdbcClient.sql("SELECT * FROM location WHERE user = :user AND home = :home")
            .param("user", user)
            .param("home", home)
            .query(Location.class)
            .optional();
    }

    public void deleteByUserHome(String user, boolean home) {
        var updated = jdbcClient.sql("DELETE FROM location WHERE user = :user AND home = :home")
            .param("user", user)
            .param("home", home)
            .update();
        Assert.state(updated == 1, "Failed to delete home location from " + user);
    }

    public void deleteAll() {
        jdbcClient.sql("DELETE FROM location")
            .update();
    }

    public List<Location> findAll() {
        return jdbcClient.sql("SELECT * FROM location")
            .query(Location.class)
            .list();
    }

    public void create(Location location) {
        jdbcClient.sql("INSERT INTO location (lat, lon, city, state, country, user, home) VALUES (:lat, :lon, :city, :state, :country, :user, :home)")
            .param("lat", location.lat())
            .param("lon", location.lon())
            .param("city", location.city())
            .param("state", location.state())
            .param("country", location.country())
            .param("user", location.user())
            .param("home", location.home())
            .update();
    }

    public void updateHomeByUser(Location newHome, String user) {
        jdbcClient.sql("UPDATE location SET home = false WHERE user = :user")
            .param("user", user)
            .update();
        jdbcClient.sql("UPDATE location SET home = true WHERE user = :user AND lat = :lat AND lon = :lon")
            .param("user", user)
            .param("lat", newHome.lat())
            .param("lon", newHome.lon())
            .update();
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM location")
            .query()
            .listOfRows()
            .size();
    }






    
}
