package com.c4n.c4n_weather;

import java.util.ArrayList;
import java.util.Collections;
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

    // Initialize all of the aspects that UserService interacts with
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private All_LocationsRepository all_LocationsRepository;
    private final WeatherService weatherService;
    private EmailService emailService;

    // Constructor for UserService
    @Autowired
    public UserService(UserRepository userRepository, LocationRepository locationRepository, All_LocationsRepository all_LocationsRepository, WeatherService weatherService, EmailService emailService) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.all_LocationsRepository = all_LocationsRepository;
        this.weatherService = weatherService;
        this.emailService = emailService;
    }

    // Function that creates a user account on the signup page
    public String createUserAccount(SignupForm signupForm){
        System.out.println("\n\n\nCity and state" + signupForm.getCity() + " " + signupForm.getState() + "\n\n\n");
        // if the username already exists, throw an exception
        if(userRepository.findByUsername(signupForm.getUsername()).isPresent()){
            throw new RuntimeException("Email is already in use.");
        }
        // if name is not only letters or spaces, throw an exception
        if(!signupForm.getName().matches("[a-zA-Z ]+")){
            throw new RuntimeException("Name may only contain letters.");
        }
        // if email is not a valid email address, throw an exception
        if(!signupForm.getUsername().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            throw new RuntimeException("Invalid email address.");
        }
        // if password is less than 5 letters or longer than 60 letters, throw an exception
        // these password requirements are arbitrary and can be changed as desired by the client
        if(signupForm.getPassword().length() < 5 || signupForm.getPassword().length() > 60){
            throw new RuntimeException("Password must be between 5 and 60 characters.");
        }
        // if city and state combination are not found throw an exception
        if(!all_LocationsRepository.getLocationByCityStateID(signupForm.getCity(), signupForm.getState()).isPresent()){
            throw new RuntimeException("City and State combination not found.");
        }

        // create a user in the database and redirect to the login page
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName(), null);
        All_Locations tempLocation = all_LocationsRepository.getLocationByCityStateID(signupForm.getCity(), signupForm.getState()).get();
        Location location = new Location(tempLocation.getLat(), tempLocation.getLon(), signupForm.getUsername(), true);
        userRepository.create(user);
        locationRepository.addLocationByUser(location, user.getUsername());
        return "redirect:/";
    }

    // Function that emails the user a code based on their email address
    public String forgotPassword(String email){
        // if the user doesn't exist, throw an exception
        if(!userRepository.findByUsername(email).isPresent()){
            throw new RuntimeException("Email is incorrect or does not exist.");
        }
        
        // make a user object based on the email address, and get their name
        User user = userRepository.findByUsername(email).get();
        String name = user.getName();

        // Generate a random 5 character code
        String code = "";
        for(int i = 0; i < 5; i++){
            code += (char)((int)(Math.random() * 26) + 97);
        }

        // use emailService API call to email the user their forgot password code
        emailService.sendSimpleMessage(name, email, code);
        // update the user's code in the database and send them to the password reset page
        userRepository.setCodeByUsername(email, code);
        return "redirect:/passwordReset";
    }

    // Function that resets the user's password based on correct code entered
    public String passwordReset(PasswordResetForm passwordResetForm){
        // if the user doesn't exist, throw an exception
        if(!userRepository.findByUsername(passwordResetForm.getEmail()).isPresent()){
            throw new RuntimeException("Email is incorrect or does not exist.");
        }
        // if the code entered is not 5 digits long, throw an exception
        if(passwordResetForm.getCode().length() != 5){
            throw new RuntimeException("Code is incorrect.");
        }
        // if the code entered is not the same as the stored code in the database, throw an exception
        if(!userRepository.findByUsername(passwordResetForm.getEmail()).get().getCode().equals(passwordResetForm.getCode())){
            throw new RuntimeException("Code is incorrect.");
        }
        // if the new password doesn't meet password requirements, throw an exception
        if(passwordResetForm.getNewPassword().length() < 5 || passwordResetForm.getNewPassword().length() > 50){
            throw new RuntimeException("Password must be between 5 and 60 characters.");
        }
        // if the new passwords do not match, throw an exception
        if(!passwordResetForm.passwordsMatch()){
            throw new RuntimeException("Passwords do not match.");
        }
        // update the user's password in the database
        userRepository.updatePasswordByUsername(passwordResetForm.getEmail(), passwordResetForm.getNewPassword());
        // reset the user's code in the database to null to prevent future password resets with the same code
        userRepository.setCodeByUsername(passwordResetForm.getEmail(), null);
        // send the user to the login page
        return "redirect:/";
    }

    // Function that displays the user's home page based on their default home location
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

        // Get the list of all user locations from the locationRepository and their respective names from the all_locations repository
        List<Location> locations = locationRepository.findAllByUser(username);
        List<All_Locations> allLocations = new ArrayList<>();
        int index = 0;
        for(Location userLocation : locations){
            allLocations.add(all_LocationsRepository.getLocationByLatLon(userLocation.getLat(), userLocation.getLon()).get());
            if(userLocation.isHome() && index!=0){
                Collections.swap(allLocations, 0, index);
            }
            index++;
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

    // Function that searches for a new location based on the user's input, and adds it to their saved locations if it is not already there and there are less than 12 saved locations
    public String search(String searchLocation, String username, Model model, HttpSession session) {
        // Verify the seach format is 'city, state' and nothing else
        if(!searchLocation.matches("^[a-zA-Z ]+, ?[a-zA-Z ]+$")){
            throw new RuntimeException("Invalid search format. Please search as 'City, State'");
        }
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

        // Create a new location object with the lat and lon values from the all_locations object and add it to the location repository if there are less than 12 locations saved
        if(((List<All_Locations>) session.getAttribute("allLocations")).size() < 12){
            Location newLocation = new Location(location.getLat(), location.getLon(), username, false);
            locationRepository.addLocationByUser(newLocation, username);
        }
        // Get the weather data from the weather service
        Weather weather = weatherService.getWeatherData(Double.toString(location.getLat()), Double.toString(location.getLon()));

        // Get the list of all user locations from the locationrepository and their respective names from the all_locations repository
        List<Location> locations = locationRepository.findAllByUser(username);
        List<All_Locations> allLocations = new ArrayList<>();
        int index = 0;
        for(Location userLocation : locations){
            allLocations.add(all_LocationsRepository.getLocationByLatLon(userLocation.getLat(), userLocation.getLon()).get());
            // swap the home location to the first index in the list
            if(userLocation.isHome() && index!=0){
                Collections.swap(allLocations, 0, index);
            }
            index++;
        }

        // add allLocations list, weather, and CityState to the model and session
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("weather", weather);
        model.addAttribute("CityState", CityState);
        session.setAttribute("allLocations", allLocations);
        session.setAttribute("weather", weather);
        session.setAttribute("CityState", CityState);
        return "main";
    }

    // Function that changes the displayed location based on the user clicking on a different saved location in the sidebar
    public String changeLocation(int index, Model model, HttpSession session){
        // get the list of saved locations from the user's current session
        List<All_Locations> allLocations = (List<All_Locations>) session.getAttribute("allLocations");
        All_Locations location;
        // if this list is null, redirect to the userView page
        if(allLocations == null){
            return "redirect:/userView";
        }
        // if the index is greater than the size of the list, redirect to the userView page (could only occur if the user was to type the url manually)
        else if(index >= allLocations.size()){
            return "redirect:/userView";
        }
        // otherwise, get the allLocations object at the index
        else{
            location = allLocations.get(index);
        }
        // get the weather data from the weather service and the city and state name from the all_locations object
        Weather weather = weatherService.getWeatherData(Double.toString(location.getLat()), Double.toString(location.getLon()));
        String CityState = location.getCityStateID();

        // add allLocations list, weather, and CityState to the model and session
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("weather", weather);
        model.addAttribute("CityState", CityState);
        session.setAttribute("allLocations", allLocations);
        session.setAttribute("weather", weather);
        session.setAttribute("CityState", CityState);

        return "main";
    }

    // Function that deletes a saved location from the user's saved locations
    public String deleteLocation(int index, String username, Model model, HttpSession session){
        // get the list of saved locations from the user's current session
        List<All_Locations> allLocations = ((List<All_Locations>)session.getAttribute("allLocations"));
        // if the index is greater than the size of the list or is 0 (0 is where the user's home location is stored - undeleteable), redirect to the userView page
        if(index >= allLocations.size() || index == 0){
            return "redirect:/userView";
        }
        // delete the location from the location repository
        locationRepository.deleteByUserLatLon(username, allLocations.get(index).getLat(), allLocations.get(index).getLon());
        // swap the location to be deleted with the last location in the list and remove the last location
        Collections.swap(allLocations, index, allLocations.size()-1);
        allLocations.remove(allLocations.size()-1);

        // add allLocations list, weather, and CityState to the model and updated allLocations list to the session
        model.addAttribute("allLocations", allLocations);
        session.setAttribute("allLocations", allLocations);
        model.addAttribute("weather", session.getAttribute("weather"));
        model.addAttribute("CityState", session.getAttribute("CityState"));

        return "main";
    }

    public String changeHome(int index, String username, Model model, HttpSession session){
        // get the list of saved locations from the user's current session
        List<All_Locations> allLocations = ((List<All_Locations>)session.getAttribute("allLocations"));
        // if the index is greater than the size of the list or is 0 (0 is already the home location), redirect to the userView page
        if(index >= allLocations.size() || index == 0){
            return "redirect:/userView";
        }

        // create a newHome location object based on the selected index lat and lon
        Location newHome = new Location(allLocations.get(index).getLat(), allLocations.get(index).getLon(), username, true);

        // update the user's home location in the location repository and swap the new home location with to the first index in the list so it appears on top
        locationRepository.updateHomeByUser(newHome, username);
        Collections.swap(allLocations, 0, index);

        return "redirect:/userView";
    }
}
