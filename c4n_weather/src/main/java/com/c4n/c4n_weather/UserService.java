package com.c4n.c4n_weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.c4n.c4n_weather.Locations.*;
import com.c4n.c4n_weather.Users.*;

import jakarta.servlet.http.HttpSession;

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

    public String userView(String username, Model model, HttpSession session) {
        // Get the user's home location from the location repository
        Location location = locationRepository.getUserHome(username).get();
        // Lat and lon values retrieved from location object, stored in strings to pass to api call
        String lat = Double.toString(location.getLat());
        String lon = Double.toString(location.getLon());

        // Get the weather data from the weather service
        Weather weather = weatherService.getWeatherData(lat, lon);

        // Get the city and state name from the all_locations repository
        Optional<All_Locations> tempLocation = all_LocationsRepository.getLocationByLatLon(location.getLat(), location.getLon());
        All_Locations locationName = tempLocation.get();
        String CityState = locationName.getCityStateID();

        // Get the list of all user locations from the locationrepository and their respective names from the all_locations repository
        List<Location> locations = locationRepository.findAllByUser(username);
        List<All_Locations> allLocations = new ArrayList<>();
        for(Location userLocation : locations){
            allLocations.add(all_LocationsRepository.getLocationByLatLon(userLocation.getLat(), userLocation.getLon()).get());
        }

        // Add allLocations list, weather, and CityState to the model and session
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("weather", weather);
        model.addAttribute("CityState", CityState);
        session.setAttribute("allLocations", allLocations);
        session.setAttribute("weather", weather);
        session.setAttribute("CityState", CityState);
        return "main";
    }

    public String search(String searchLocation, String username, Model model, HttpSession session) {
        // Get the city and state from the searchLocation string
        String city = searchLocation.split(",")[0].trim();
        String state = searchLocation.split(",")[1].trim();
        All_Locations location;
        // Check if the city and state combination is in the all_locations repository, if it is not, throw a runtime exception
        // If state is 2 characters, search by city and state id
        if(state.length() == 2){
            if(!all_LocationsRepository.getLocationByCityStateID(city, state).isPresent()){
                throw new RuntimeException("City and State combination not found.");
            }
            else{
                location = all_LocationsRepository.getLocationByCityStateID(city, state).get();
            }
        }
        // If state is not 2 characters, search by city and state name
        else{
            if(!all_LocationsRepository.getLocationByCityStateName(city, state).isPresent()){
                throw new RuntimeException("City and State combination not found.");
            }
            else{
                location = all_LocationsRepository.getLocationByCityStateName(city, state).get();
            }
        }
        String CityState = location.getCityStateID();

        // Create a new location object with the lat and lon values from the all_locations object and add it to the location repository
        Location newLocation = new Location(location.getLat(), location.getLon(), username, false);
        locationRepository.addLocationByUser(newLocation, username);
        // Get the weather data from the weather service
        Weather weather = weatherService.getWeatherData(Double.toString(location.getLat()), Double.toString(location.getLon()));

        // Get the list of all user locations from the locationrepository and their respective names from the all_locations repository
        List<Location> locations = locationRepository.findAllByUser(username);
        List<All_Locations> allLocations = new ArrayList<>();
        for(Location userLocation : locations){
            allLocations.add(all_LocationsRepository.getLocationByLatLon(userLocation.getLat(), userLocation.getLon()).get());
        }

        // Add allLocations list, weather, and CityState to the model and session
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("weather", weather);
        model.addAttribute("CityState", CityState);
        session.setAttribute("allLocations", allLocations);
        session.setAttribute("weather", weather);
        session.setAttribute("CityState", CityState);
        return "main";
    }

    public String changeLocation(int index, Model model, HttpSession session){
        List<All_Locations> allLocations = (List<All_Locations>) session.getAttribute("allLocations");
        All_Locations location;
        if(allLocations == null){
            return "redirect:/userView";
        }
        else if(index >= allLocations.size()){
            return "redirect:/userView";
        }
        else{
            location = allLocations.get(index);
        }
        Weather weather = weatherService.getWeatherData(Double.toString(location.getLat()), Double.toString(location.getLon()));
        String CityState = location.getCityStateID();


        model.addAttribute("allLocations", allLocations);
        model.addAttribute("weather", weather);
        model.addAttribute("CityState", CityState);
        session.setAttribute("allLocations", allLocations);
        session.setAttribute("weather", weather);
        session.setAttribute("CityState", CityState);

        return "main";
    }
}
