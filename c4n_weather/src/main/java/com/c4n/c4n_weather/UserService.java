package com.c4n.c4n_weather;

import com.c4n.c4n_weather.Locations.Weather;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.c4n.c4n_weather.Locations.Location;
import com.c4n.c4n_weather.Locations.LocationRepository;
import com.c4n.c4n_weather.Users.LoginForm;
import com.c4n.c4n_weather.Users.SignupForm;
import com.c4n.c4n_weather.Users.User;
import com.c4n.c4n_weather.Users.UserRepository;
import com.google.common.cache.Weigher;

import reactor.core.publisher.Mono;

@Service
public class UserService {
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private final WebClient webClient;

    @Autowired
    public UserService(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.webClient = WebClient.create();
    }

    //this is the function that creates a user account on the signup page
    public String createUserAccount(SignupForm signupForm){
        //checks if the username is already in the database
        if(userRepository.findByUsername(signupForm.getUsername()).isPresent()){
            //this needs to be changed to probably just show a popup of "username already exists"
            return "redirect:/signup";
        }
        //if the username is not in the database, it will add the user to the database
        //and redirect to the login page
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName());
        Location location = new Location(0.00, 0.00, signupForm.getCity(), signupForm.getState(), signupForm.getUsername(), true);
        locationRepository.create(location);
        userRepository.create(user);
        return "redirect:/";
    }

    //this is the function that logs a user in on the login page
    public String userLogin(@Valid LoginForm loginForm) {
        //verifies username is in the database, if it is not, it will redirect to userNotFound
        if(!userRepository.findByUsername(loginForm.getUsername()).isPresent()){
            //this needs to be changed to probably just show a popup of "invalid username"
            return "redirect:/userNotFound";
        }

        //verifies password is correct, if it is not, it will redirect to passwordNotFound
        //We also need to make sure that we hash the password and enter it to the DB then compare the hashed password
        if(!userRepository.findByUsername(loginForm.getUsername()).get().getPassword().equals(loginForm.getPassword())){
            //this needs to be changed to probably just show a popup of "invalid password"
            return "redirect:/passwordNotFound";
        }

        // call API here
        // this is the call for what the user currently has stored as their home location - calls as user logs in to get weather data
            // Location object retrieved from locationRepository
            Location location = locationRepository.findByUser(loginForm.getUsername()).get();
            // lat and lon values retrieved from location object, stored in strings to pass to api call
            String lat = location.getLat();
            String lon = location.getLon();
            // String part = "minutely"; // replace with the part you want to exclude if filters are desired
            // store key locally (for now) - will need to be stored in a more secure way
            String apiKey = "ee0669cae800f45f75666b998e44ec77"; // replace with your actual API key
            // create url for api call, concatenate lat, lon, part, and api key
            String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&exclude=" + "&appid=" + apiKey;
            // use webClient to call the API, .uri() method to pass the url, .retrieve() method to retrieve the response, .bodyToMono() method to convert the response to a Mono object
            Mono<Weather> weatherData = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Weather.class);
                // weatherData now contains the weather data from the API call
                // you need to subscribe to the Mono to trigger the API call and get the data.

        //if both username and password are correct, it will redirect to userView
        //which will be our weather page when done
        return "redirect:/userView";
    }
}
