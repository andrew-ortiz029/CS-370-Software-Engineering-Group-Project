package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import; 
import org.springframework.security.crypto.password.PasswordEncoder;

import com.c4n.c4n_weather.Users.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import({JdbcUserRepository.class, PasswordEncoderConfig.class})
class JdbcUserRepositoryTest {

    @Autowired
    JdbcUserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    User user = new User("test4", "password", "Test User4", null);

    @Test
    void testCount(){
        assertEquals(3, repository.count());
    }

    @Test
    void testCreate(){
        assertEquals(3, repository.count());
        repository.create(user);
        assertEquals(4, repository.count());
    }

    @Test
    void testFindByUsername() {
        Optional<User> user = repository.findByUsername("test1");
        assertTrue(user.isPresent());
        assertEquals("Test User1", user.get().name());
        Optional<User> user1 = repository.findByUsername("notFound");
        assertFalse(user1.isPresent());
    }

    @Test
    void testFindAll(){
        List<User> users = repository.findAll();
        assertEquals("Test User1", users.get(0).getName());
        assertEquals("Test User2", users.get(1).getName());
        assertEquals("Test User3", users.get(2).getName());
    }

    @Test
    void testDeleteByUsername(){
        assertEquals(3, repository.count());
        repository.deleteByUsername("test1");
        assertEquals(2, repository.count());
        repository.deleteByUsername("test2");
        assertEquals(1, repository.count());
        repository.deleteByUsername("test3");
        assertEquals(0, repository.count());
    }

    @Test
    void testDeleteAll(){
        assertEquals(3, repository.count());
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

    @Test
    void testPasswordHashed(){
        repository.create(user);
        User query = repository.findByUsername(user.getUsername()).get();
        assertNotEquals(query.getPassword(), user.getPassword());
    }

    @Test
    void testUpdatePasswordByUsername(){
        User query = repository.findByUsername("test1").get();
        assertEquals(query.getPassword(), "password");
        repository.updatePasswordByUsername(query.getUsername(), "newPassword");
        query = repository.findByUsername("test1").get();
        assertNotEquals(query.getPassword(), "newPassword");
    }

    @Test
    void testSetCodeByUsername(){
        User query = repository.findByUsername("test1").get();
        assertEquals(query.getCode(), null);
        repository.setCodeByUsername(query.getUsername(), "1234");
        query = repository.findByUsername("test1").get();
        assertEquals(query.getCode(), "1234");
    }
}