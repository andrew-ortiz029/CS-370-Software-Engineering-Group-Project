package com.c4n.c4n_weather.Locations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository{
    List<Location> findByUser(String user);
    void deleteByUser(String user);

    Optional<Location> findByUserHome(String user, boolean home);
    void deleteByUserHome(String user, boolean home);

    void deleteAll();

    List<Location> findAll();

    void create(Location location);

    void updateHomeByUser(Location newHome, String user);

    int count();
    
}
