package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import com.c4n.c4n_weather.Locations.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import(JdbcLocationRepository.class)
public class JdbcLocationRepositoryTest {

    @Autowired
    JdbcLocationRepository repository;
    
    @Test
    void testFindAllByUser(){
        List<Location> locations = repository.findAllByUser("user1");
        assertEquals(3, locations.size());
        assertEquals(1.1111, locations.get(0).getLat());
        assertEquals(2.2222, locations.get(1).getLat());
        assertEquals(3.3333, locations.get(2).getLat());
    }

    @Test
    void testGetUserHome(){
        Optional<Location> location = repository.getUserHome("user1");
        assertTrue(location.isPresent());
        assertEquals(1.1111, location.get().getLat());
    }

    @Test
    void testUpdateHomeByUser(){
        List<Location> locations = repository.findAllByUser("user1");
        repository.updateHomeByUser(locations.get(2), "user1");
        assertNotEquals(1.1111, repository.getUserHome("user1").get().getLat());
        assertEquals(3.3333, repository.getUserHome("user1").get().getLat());
    }

    @Test
    void testDeleteByUserLatLon(){
        List<Location> locations = repository.findAllByUser("user1");
        assertEquals(3, locations.size());
        Location location = locations.get(1);
        repository.deleteByUserLatLon("user1", location.getLat(), location.getLon());
        locations = repository.findAllByUser("user1");
        assertEquals(2, locations.size());
    }

    @Test
    void testAddLocationByUser(){
        List<Location> locations = repository.findAllByUser("user1");
        assertEquals(3, locations.size());
        Location location = new Location(4.4444, 4.4444, "user1", false);
        repository.addLocationByUser(location, "user1");
        locations = repository.findAllByUser("user1");
        assertEquals(4, locations.size());
    }
}
