package com.c4n.c4n_weather;

import com.c4n.c4n_weather.Locations.Weather;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.c4n.c4n_weather.Locations.All_Locations;
import com.c4n.c4n_weather.Locations.All_LocationsRepository;
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
    private All_LocationsRepository all_LocationsRepository;


    @Autowired
    public UserService(UserRepository userRepository, LocationRepository locationRepository, All_LocationsRepository all_LocationsRepository) {
        
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.webClient = WebClient.create();
        this.all_LocationsRepository = all_LocationsRepository;

    }

    //this is the function that creates a user account on the signup page
    public String createUserAccount(SignupForm signupForm){
        //checks if the username is already in the database
        if(userRepository.findByUsername(signupForm.getUsername()).isPresent()){
            throw new RuntimeException("Email is already in use.");
        }
        //if name is not only letters, throw an exception
        if(!signupForm.getName().matches("[a-zA-Z]+")){
            throw new RuntimeException("Name may only contain letters.");
        }
        //if email is not a valid email address, throw an exception
        if(!signupForm.getUsername().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            throw new RuntimeException("Invalid email address.");
        }
        //if password is less than 5 letters or longer than 60 letters, throw an exception
        if(signupForm.getPassword().length() < 5 || signupForm.getPassword().length() > 60){
            throw new RuntimeException("Password must be between 5 and 60 characters.");
        }
        //if city and state combination are not found throw an exception
        if(signupForm.getState().length() == 2){
            if(!all_LocationsRepository.getLocationByCityStateID(signupForm.getCity(), signupForm.getState()).isPresent()){
                throw new RuntimeException("City and State combination not found.");
            }
        }
        else{
            if(!all_LocationsRepository.getLocationByCityStateName(signupForm.getCity(), signupForm.getState()).isPresent()){
                throw new RuntimeException("City and State combination not found.");
            }
        }
        //if the username is not in the database, it will add the user to the database
        //and redirect to the login page
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName());
        All_Locations tempLocation;
        if(signupForm.getState().length() == 2){
            tempLocation = all_LocationsRepository.getLocationByCityStateID(signupForm.getCity(), signupForm.getState()).get();
        }
        else{
            tempLocation = all_LocationsRepository.getLocationByCityStateName(signupForm.getCity(), signupForm.getState()).get();
        }
        Location location = new Location(tempLocation.getLat(), tempLocation.getLon(), signupForm.getUsername(), true);
        userRepository.create(user);
        locationRepository.addLocationByUser(location, user.getUsername());
        return "redirect:/";
    }

    //this is the function that logs a user in on the login page
    public String userLogin(@Valid LoginForm loginForm, User user) {

        //if we got this far, the username and password are correct (checked in userController) -> need to call api and load the api onto the main page and reroute to it

        // call API here
        // this is the call for what the user currently has stored as their home location - calls as user logs in to get weather data
        // Location object retrieved from locationRepository
        Location location = locationRepository.getUserHome(loginForm.getUsername()).get();
        // lat and lon values retrieved from location object, stored in strings to pass to api call
        String lat = Double.toString(location.getLat());
        String lon = Double.toString(location.getLon());
        // String part = "minutely"; // replace with the part you want to exclude if filters are desired
        // store key locally (for now) - will need to be stored in a more secure way
        String apiKey = "nwndo12odi32oid32od2"; // replace with your actual API key
        // create url for api call, concatenate lat, lon, part, and api key
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&exclude=" + "&appid=" + apiKey;
        // use webClient to call the API, .uri() method to pass the url, .retrieve() method to retrieve the response, .bodyToMono() method to convert the response to a Mono object
        Mono<Weather> weatherData = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Weather.class);
            // weatherData now contains the weather data from the API call
            // you need to subscribe to the Mono to trigger the API call and get the data.

        
        //redirect to the main page - will need to pass the weather data to the main page - will change userView to proper html file
        return "redirect:/userView";
    }
}
