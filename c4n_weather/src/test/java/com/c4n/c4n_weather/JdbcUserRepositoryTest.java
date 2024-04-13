package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import com.c4n.c4n_weather.Users.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import(JdbcUserRepository.class)
class JdbcUserRepositoryTest {

    @Autowired
    JdbcUserRepository repository;

    User user = new User("test4", "password", "Test User4");

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
    void testPasswordHashing(){
        String password = "password";
        assertNotEquals(password, PasswordHasher.get_SHA_256_SecurePassword(password));
    }

    @Test
    void testPasswordHashed(){
        repository.create(user);
        assertNotEquals(user.getPassword(), repository.findByUsername("test4").get().getPassword());
    }

    @Test
    void testVerifyPassword(){
        assertEquals("password", user.getPassword());
        repository.create(user);
        assertTrue(PasswordHasher.verifyPassword(user.getPassword(), repository.findByUsername("test4").get().getPassword()));
        assertFalse(PasswordHasher.verifyPassword("wrongPassword", repository.findByUsername("test4").get().getPassword()));
    }

    @Test
    void testUpdatePasswordByUsername(){
        assertEquals("password", repository.findByUsername("test1").get().getPassword());
        String newPassword = "newPassword";
        repository.updatePasswordByUsername("test1", newPassword);
        PasswordHasher.verifyPassword(newPassword, repository.findByUsername("test1").get().getPassword());
    }
}