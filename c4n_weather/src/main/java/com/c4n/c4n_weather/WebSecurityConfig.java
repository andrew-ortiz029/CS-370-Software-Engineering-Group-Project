package com.c4n.c4n_weather;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.c4n.c4n_weather.Users.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.c4n.c4n_weather.Users.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserRepository userRepository;
    

    // This method provides the basis for Spring Security to filter requests and authenticate users. Additionally, provides the custom login page
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/css/**", "/js/**", "/imgs/**", "/login", "/styles/**", "/forgotPassword", "/signup", "/passwordReset").permitAll()
                .requestMatchers("/userView").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") // Add this line if your form is submitted to a different URL.
                .usernameParameter("username") // Replace "username" with the name of your username field, if different.
                .passwordParameter("password") // Replace "password" with the name of your password field, if different.
                .defaultSuccessUrl("/userView", true)
                .permitAll()
            );
    
        return http.build();
    }

    // This method provides the logic for Spring security to authenticate user logins against the database
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (!optionalUser.isPresent()) {
                System.out.println("\n\n\nuser not found in security\n\n\n");
                throw new UsernameNotFoundException("User not found");
            }
    
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
        };
    }
}

