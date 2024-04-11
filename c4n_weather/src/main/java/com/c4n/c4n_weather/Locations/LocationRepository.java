package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository{

    void addLocationByUser(Location location, String user);
    void deleteByUserLatLon(String user, double lat, double lon);
    void updateHomeByUser(Location newHome, String user);
    List<Location> findAllByUser(String user);
    Optional<Location> getUserHome(String user);
    
}
