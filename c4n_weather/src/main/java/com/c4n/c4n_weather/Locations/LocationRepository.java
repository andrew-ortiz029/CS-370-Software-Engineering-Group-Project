package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.util.Optional;

public interface LocationRepository{
    List<Location> findByUser(String user);
    void deleteByUser(String user);

    Optional<Location> findByUserHome(String user, boolean home);
    void deleteByUserHome(String user, boolean home);

    // Location findByCityStateCountry(String city, String state, String country);
    // void deleteByCityStateCountry(String city, String state, String country);

    void deleteAll();

    List<Location> findAll();

    void create(Location location);

    void updateHomeByUser(Location newHome, String user);

    int count();
    
}
