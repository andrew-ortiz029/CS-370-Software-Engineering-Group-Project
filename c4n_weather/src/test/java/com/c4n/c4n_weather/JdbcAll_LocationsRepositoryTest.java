package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import com.c4n.c4n_weather.Locations.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@JdbcTest
@Import(JdbcAll_LocationsRepository.class)
public class JdbcAll_LocationsRepositoryTest {

    @Autowired
    JdbcAll_LocationsRepository repository;
    
    @Test
    void testGetLocationByCityStateID() {
        Optional<All_Locations> location = repository.getLocationByCityStateID("New York", "NY");
        assertFalse(location.isPresent());
        location = repository.getLocationByCityStateID("City1", "CA");
        assertTrue(location.isPresent());
        assertEquals(1.1111, location.get().getLat());
    }

    @Test
    void testGetLocationByCityStateName() {
        Optional<All_Locations> location = repository.getLocationByCityStateName("New York", "New York");
        assertFalse(location.isPresent());
        location = repository.getLocationByCityStateName("City1", "California");
        assertTrue(location.isPresent());
        assertEquals(1.1111, location.get().getLat());
    }

    @Test
    void testGetLocationByLatLon() {
        Optional<All_Locations> location = repository.getLocationByLatLon(3.3333, 3.3333);
        assertFalse(location.isPresent());
        location = repository.getLocationByLatLon(1.1111, 1.1111);
        assertTrue(location.isPresent());
        assertEquals("City1, CA", location.get().getCityStateID());
    }
}
