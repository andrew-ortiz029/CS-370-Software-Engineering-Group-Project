package com.c4n.c4n_weather.Locations;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface All_LocationsRepository{

    Optional<All_Locations> getLocationByCityStateID(String city, String stateID);
    Optional<All_Locations> getLocationByCityStateName(String city, String stateName);
    Optional<All_Locations> getLocationByLatLon(double lat, double lon);
}
