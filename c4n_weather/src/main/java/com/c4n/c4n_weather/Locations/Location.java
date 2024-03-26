package com.c4n.c4n_weather.Locations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Location(
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    float lat,
    float lon,
    String city,
    String state,
    String country,
    String user,
    boolean home
) {
    public Location {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }
    
    public String getCityStateCountry() {
        return String.format("%s, %s, %s", city, state, country);
    }

    public String getLat() {
        return String.format("%d", lat);
    }

    public String getLatLon(){
        return String.format("lat=%d&lon=%d", lat, lon);
    }
}
