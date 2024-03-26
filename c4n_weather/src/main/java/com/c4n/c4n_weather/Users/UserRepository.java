package com.c4n.c4n_weather.Users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);

    void deleteAll();

    List<User> findAll();

    void create(User user);

    void updatePasswordByUsername(String username, String newPassword);
    
    int count();   
}
