package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcLocationRepository implements LocationRepository{

    private final JdbcClient jdbcClient;

    public JdbcLocationRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void addLocationByUser(Location location, String user) {
        Assert.notNull(location, "Location must not be null");
        Assert.notNull(user, "User must not be null");
        jdbcClient.sql("INSERT INTO location (lat, lon, user, home) VALUES (:lat, :lon, :user, :home)")
            .param("lat", location.getLat())
            .param("lon", location.getLon())
            .param("user", user)
            .param("home", location.isHome())
            .update();
    }

    public void deleteByUserLatLon(String user, double lat, double lon) {
        Assert.notNull(user, "User must not be null");
        jdbcClient.sql("DELETE FROM location WHERE user = :user AND lat = :lat AND lon = :lon")
            .param("user", user)
            .param("lat", lat)
            .param("lon", lon)
            .update();
    }

    public void updateHomeByUser(Location newHome, String user) {
        Assert.notNull(newHome, "Location must not be null");
        Assert.notNull(user, "User must not be null");
        jdbcClient.sql("UPDATE location SET home = false WHERE user = :user")
            .param("user", user)
            .update();
        jdbcClient.sql("UPDATE location SET home = true WHERE user = :user AND lat = :lat AND lon = :lon")
            .param("user", user)
            .param("lat", newHome.getLat())
            .param("lon", newHome.getLon())
            .update();
    }

    public List<Location> findAllByUser(String user) {
        Assert.notNull(user, "User must not be null");
        return jdbcClient.sql("SELECT * FROM location WHERE user = :user")
            .param("user", user)
            .query(Location.class)
            .list();
    }

    public Optional<Location> getUserHome(String user) {
        Assert.notNull(user, "User must not be null");
        return jdbcClient.sql("SELECT * FROM location WHERE user = :user AND home = true")
            .param("user", user)
            .query(Location.class)
            .optional();
    }

    
}
