package com.c4n.c4n_weather;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c4n.c4n_weather.Locations.All_Locations;
import com.c4n.c4n_weather.Locations.All_LocationsRepository;
import com.c4n.c4n_weather.Locations.Location;
import com.c4n.c4n_weather.Locations.LocationRepository;
import com.c4n.c4n_weather.Users.SignupForm;
import com.c4n.c4n_weather.Users.LoginForm;
import com.c4n.c4n_weather.Users.User;
import com.c4n.c4n_weather.Users.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private All_LocationsRepository all_LocationsRepository;

    @Autowired
    public UserService(UserRepository userRepository, LocationRepository locationRepository, All_LocationsRepository all_LocationsRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.all_LocationsRepository = all_LocationsRepository;
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
        //if both username and password are correct, it will redirect to userView
        //which will be our weather page when done
        return "redirect:/userView";
    }
}
