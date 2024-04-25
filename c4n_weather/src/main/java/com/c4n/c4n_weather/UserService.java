package com.c4n.c4n_weather;

import com.c4n.c4n_weather.Locations.Weather;

import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ui.Model;


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
import com.c4n.c4n_weather.Locations.*;
import com.c4n.c4n_weather.Users.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Service
public class UserService {

    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private All_LocationsRepository all_LocationsRepository;
    private final WeatherService weatherService;
    private EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, LocationRepository locationRepository, All_LocationsRepository all_LocationsRepository, WeatherService weatherService, EmailService emailService) {
        
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.all_LocationsRepository = all_LocationsRepository;
        this.weatherService = weatherService;
        this.emailService = emailService;

    }

    //this is the function that creates a user account on the signup page
    public String createUserAccount(SignupForm signupForm){
        //checks if the username is already in the database
        if(userRepository.findByUsername(signupForm.getUsername()).isPresent()){
            throw new RuntimeException("Email is already in use.");
        }
        //if name is not only letters, throw an exception
        // update name validation to allow spaces
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
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName(), null);
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
    public String userLogin(@Valid LoginForm loginForm, User user, RedirectAttributes redirectAttributes) {
        // API CALL BEGIN
        // this is the call for what the user currently has stored as their home location - calls as user logs in to get weather data
        // Location object retrieved from locationRepository
        Location location = locationRepository.getUserHome(loginForm.getUsername()).get();
        // lat and lon values retrieved from location object, stored in strings to pass to api call
        String lat = Double.toString(location.getLat());
        String lon = Double.toString(location.getLon());
        Weather weather = weatherService.getWeatherData(lat, lon);

        // print used for testing purposes
        System.out.println(weather.toString());

        // adding weather object returned from API call, 
        redirectAttributes.addFlashAttribute("weather", weather);

        Optional<All_Locations> tempLocation = all_LocationsRepository.getLocationByLatLon(location.getLat(), location.getLon());
        All_Locations locationName = tempLocation.get();
        
        String CityState = locationName.getCityStateID();
        redirectAttributes.addFlashAttribute("CityState", CityState);

        return "redirect:/userView";
    }

    public String forgotPassword(String email){
        // Verify the user exists, or pass back a runtime exception


        if(!userRepository.findByUsername(email).isPresent()){
            throw new RuntimeException("Email is incorrect or does not exist.");
        }
        User user = userRepository.findByUsername(email).get();
        String name = user.getName();
        // Generate a random 5 character code
        String code = "";
        for(int i = 0; i < 5; i++){
            code += (char)((int)(Math.random() * 26) + 97);
        }
        emailService.sendSimpleMessage(name, email, code);
        userRepository.setCodeByUsername(email, code);
        return "redirect:/passwordReset";
    }

    public String passwordReset(PasswordResetForm passwordResetForm){
        // Verify the user exists, or pass back a runtime exception
        if(!userRepository.findByUsername(passwordResetForm.getEmail()).isPresent()){
            throw new RuntimeException("Email is incorrect or does not exist.");
        }
        // Verify the code is correct, or pass back a runtime exception
        if(passwordResetForm.getCode().length() != 5){
            throw new RuntimeException("Code is incorrect.");
        }
        if(!userRepository.findByUsername(passwordResetForm.getEmail()).get().getCode().equals(passwordResetForm.getCode())){
            throw new RuntimeException("Code is incorrect.");
        }
        // Verify the password is between 5 and 60 characters, or pass back a runtime exception
        if(passwordResetForm.getNewPassword().length() < 5 || passwordResetForm.getNewPassword().length() > 50){
            throw new RuntimeException("Password must be between 5 and 60 characters.");
        }
        // Verify the password and confirm password match, or pass back a runtime exception
        if(!passwordResetForm.passwordsMatch()){
            throw new RuntimeException("Passwords do not match.");
        }
        // Update the user's password in the database
        userRepository.updatePasswordByUsername(passwordResetForm.getEmail(), passwordResetForm.getNewPassword());
        return "redirect:/";
    }
}
