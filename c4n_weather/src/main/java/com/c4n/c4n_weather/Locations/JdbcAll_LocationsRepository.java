package com.c4n.c4n_weather.Locations;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAll_LocationsRepository implements All_LocationsRepository{

    private final JdbcClient jdbcClient;

    public JdbcAll_LocationsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<All_Locations> getLocationByCityStateID(String city, String stateID) {
        return jdbcClient.sql("SELECT * FROM all_locations WHERE city = :city AND state_id = :stateID")
            .param("city", city)
            .param("stateID", stateID)
            .query(All_Locations.class)
            .optional();
    }

    public Optional<All_Locations> getLocationByCityStateName(String city, String stateName) {
        return jdbcClient.sql("SELECT * FROM all_locations WHERE city = :city AND state_name = :stateName")
            .param("city", city)
            .param("stateName", stateName)
            .query(All_Locations.class)
            .optional();
    }

    public Optional<All_Locations> getLocationByLatLon(double lat, double lon) {
        return jdbcClient.sql("SELECT * FROM all_locations WHERE lat = :lat AND lon = :lon")
            .param("lat", lat)
            .param("lon", lon)
            .query(All_Locations.class)
            .optional();
    }
}
