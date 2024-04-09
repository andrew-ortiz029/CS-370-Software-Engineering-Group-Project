package com.c4n.c4n_weather;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
<<<<<<< Updated upstream
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.c4n.c4n_weather.Users.User;
=======
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
>>>>>>> Stashed changes

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class C4nWeatherApplication {

	// create logger instance to log messages
	private static final Logger log = LoggerFactory.getLogger(JakartaServletWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(C4nWeatherApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			User user = new User("username", "password", "Jake");
			log.info("User: " + user);
		};
	}

	//bean initialization to provide webclient instance
	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}

}