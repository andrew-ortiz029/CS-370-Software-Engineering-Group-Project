package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.c4n.c4n_weather.Users.*;
import com.c4n.c4n_weather.Locations.*;

import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private All_LocationsRepository all_LocationsRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock 
    WeatherService weatherService;
    @Mock
    EmailService emailService;
    @Mock
    Weather weather;
    @Mock
    Model model;
    @Mock
    HttpSession session;

    @InjectMocks
    private UserService userService;

    @InjectMocks 
    private UserController userController;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // email in use
    // name is only letters
    // email is valid
    // password is between 5 and 60 characters
    @Test
    public void testCreateUserAccount() {
        // Set up SignupForm with valid signup credentials
        SignupForm signupForm = new SignupForm("test@Username.com", "testPassword", "testName", "testCity", "testState");
        // Set up User with valid credentials
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName(), null);
        // Set up SignupForm with invalid name
        SignupForm signupForm2 = new SignupForm("test1@Username.com", "testPassword", "testName1", "testCity", "testState");
        // Set up SignupForm with invalid email
        SignupForm signupForm3 = new SignupForm("testUsername", "testPassword", "testName", "testCity", "testState");
        // Set up SignupForm with invalid password
        SignupForm signupForm4 = new SignupForm("test2@Username.com", "test", "testName", "testCity", "testState");

        List<User> users = new ArrayList<>();
        doAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            users.add(userArg);
            return null; // Return value is ignored for void methods
        }).when(userRepository).create(any(User.class));

        All_Locations location = new All_Locations("testCity", "testStateID", "testStateName", 1.1111, 1.1111);
        when(all_LocationsRepository.getLocationByCityStateID("testCity", "testState")).thenReturn(Optional.of(location));

        Location location1 = new Location(location.getLat(), location.getLon(), signupForm.getUsername(), true);
        List<Location> locations = new ArrayList<>();
        doAnswer(invocation -> {
            Location locationArg = invocation.getArgument(0);
            locations.add(locationArg);
            return null; // Return value is ignored for void methods
        }).when(locationRepository).addLocationByUser(location1, signupForm.getUsername());

        // test valid signup
        String result = userService.createUserAccount(signupForm);
        assertEquals("redirect:/", result);

        // test invalid name
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUserAccount(signupForm2);
        });
        assertEquals("Name may only contain letters.", exception.getMessage());

        // test invalid email
        Exception exception2 = assertThrows(RuntimeException.class, () -> {
            userService.createUserAccount(signupForm3);
        });
        assertEquals("Invalid email address.", exception2.getMessage());

        // test invalid password
        Exception exception3 = assertThrows(RuntimeException.class, () -> {
            userService.createUserAccount(signupForm4);
        });
        assertEquals("Password must be between 5 and 60 characters.", exception3.getMessage());

        // test duplicate email
        when(userRepository.findByUsername(signupForm.getUsername())).thenReturn(Optional.of(user));
        Exception exception4 = assertThrows(RuntimeException.class, () -> {
            userService.createUserAccount(signupForm);
        });
        assertEquals("Email is already in use.", exception4.getMessage());
    }

    @Test
    public void testForgotPassword(){
        // Set up User with valid credentials
        User user = new User("goodEmail", "testPassword", "testName", null);

        // Set up User with invalid credentials
        User user2 = new User("badEmail", "testPassword", "testName", null);

        // set up userRepository with incorrect email
        when(userRepository.findByUsername(user2.getUsername())).thenReturn(Optional.empty());

        // set up user with correct email
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // test incorrect email and verify runtime exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.forgotPassword(user2.getUsername());
        });
        assertEquals("Email is incorrect or does not exist.", exception.getMessage());

        // test correct email and verify return value
        String result = userService.forgotPassword(user.getUsername());
        assertEquals("redirect:/passwordReset", result);
    }

    @Test
    public void testResetPassword(){
        // Set up PasswordResetForm with valid email
        PasswordResetForm passwordResetForm = new PasswordResetForm("goodEmail", "12345", "newPassword", "newPassword");

        // Set up PasswordResetForm with invalid email
        PasswordResetForm passwordResetForm2 = new PasswordResetForm("badEmail", "12345", "newPassword", "newPassword");

        // Set up PasswordResetForm with invalid code - code is not 5 digits
        PasswordResetForm passwordResetForm3 = new PasswordResetForm("wrongCodeLength", "1234", "newPassword", "newPassword");

        // Set up PasswordResetForm with mismatching passwords
        PasswordResetForm passwordResetForm4 = new PasswordResetForm("goodEmail", "12345", "newPassword", "newPassword2");

        // Set up PasswordResetForm with invalid password
        PasswordResetForm passwordResetForm5 = new PasswordResetForm("goodEmail", "12345", "test", "test");

        // Test invalid email
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.passwordReset(passwordResetForm2);
        });
        assertEquals("Email is incorrect or does not exist.", exception.getMessage());

        // Test invalid code length
        when(userRepository.findByUsername(passwordResetForm3.getEmail())).thenReturn(Optional.of(new User("wrongCodeLength", "oldPassword", "testName", "12345")));
        Exception exception2 = assertThrows(RuntimeException.class, () -> {
            userService.passwordReset(passwordResetForm3);
        });
        assertEquals("Code is incorrect.", exception2.getMessage());

        // Test mismatching passwords
        when(userRepository.findByUsername(passwordResetForm4.getEmail())).thenReturn(Optional.of(new User("goodEmail", "oldPassword", "testName", "12345")));
        Exception exception3 = assertThrows(RuntimeException.class, () -> {
            userService.passwordReset(passwordResetForm4);
        });
        assertEquals("Passwords do not match.", exception3.getMessage());

        // Test invalid password
        when(userRepository.findByUsername(passwordResetForm5.getEmail())).thenReturn(Optional.of(new User("goodEmail", "oldPassword", "testName", "12345")));
        Exception exception4 = assertThrows(RuntimeException.class, () -> {
            userService.passwordReset(passwordResetForm5);
        });
        assertEquals("Password must be between 5 and 60 characters.", exception4.getMessage());

        // Test valid password reset
        String result = userService.passwordReset(passwordResetForm);
        assertEquals("redirect:/", result);
    }

    @Test
    public void testUserView(){
                // Arrange
        String username = "testUser";
        Location location = new Location(1.0, 1.0, username, true);
        when(locationRepository.getUserHome(username)).thenReturn(Optional.of(location));

        Weather weather = new Weather();
        when(weatherService.getWeatherData(anyString(), anyString())).thenReturn(weather);

        All_Locations allLocations = new All_Locations("Test City", "Test State ID", "Test State Name", 1.0, 1.0);
        when(all_LocationsRepository.getLocationByLatLon(anyDouble(), anyDouble())).thenReturn(Optional.of(allLocations));

        List<Location> locations = new ArrayList<>();
        when(locationRepository.findAllByUser(username)).thenReturn(locations);

        String result = userService.userView(username, model, session);

        // Assert
        assertEquals("main", result);
    }

    @Test
    public void testSearch() {
        // Arrange
        String searchLocation = "Test City, Test State";
        String username = "testUser";
        All_Locations allLocations = new All_Locations("Test City", "Test State ID", "Test State Name", 1.0, 1.0);
        when(all_LocationsRepository.getLocationByCityStateName(anyString(), anyString())).thenReturn(Optional.of(allLocations));

        when(all_LocationsRepository.getLocationByCityStateName(anyString(), anyString())).thenReturn(Optional.of(allLocations));

        Weather weather = new Weather();
        when(weatherService.getWeatherData(anyString(), anyString())).thenReturn(weather);

        List<Location> locations = new ArrayList<>();
        when(locationRepository.findAllByUser(username)).thenReturn(locations);

        when(((List<All_Locations>)session.getAttribute("allLocations"))).thenReturn(new ArrayList<All_Locations>());

        List<All_Locations> allLocationsSession = (List<All_Locations>) session.getAttribute("allLocations");
        if(allLocationsSession != null && allLocationsSession.size() < 12){
            Location newLocation = new Location(1.0, 1.0, username, false);
            locationRepository.addLocationByUser(newLocation, username);
        }

        String result = userService.search(searchLocation, username, model, session);

        // Assert
        assertEquals("main", result);
    }

    @Test
    public void testChangeLocation(){
        // Test null allLocations
        String result = userService.changeLocation(0, model, session);
        assertEquals("redirect:/userView", result);

        List<All_Locations> allLocations = new ArrayList<>();
        // Populate allLocations array
        for(int i=0; i<5; i++){
            allLocations.add(new All_Locations("Test City" + i, "Test State ID" + i, "Test State Name" + i, i, i));
        }

        when(session.getAttribute("allLocations")).thenReturn(allLocations);

        // Test index out of bounds
        result = userService.changeLocation(10, model, session);
        assertEquals("redirect:/userView", result);

        // Test valid index
        result = userService.changeLocation(0, model, session);
        assertEquals("main", result);
    }

    @Test
    public void testDeleteLocation(){
        List<All_Locations> allLocations = new ArrayList<>();
        // Populate allLocations array
        for(int i=0; i<5; i++){
            allLocations.add(new All_Locations("Test City" + i, "Test State ID" + i, "Test State Name" + i, i, i));
        }

        when(session.getAttribute("allLocations")).thenReturn(allLocations);

        // Test index out of bounds
        String result = userService.deleteLocation(10, "username", model, session);
        assertEquals("redirect:/userView", result);

        // Test index 0
        result = userService.deleteLocation(0, "username", model, session);
        assertEquals("redirect:/userView", result);

        // Test valid index
        int index = 1;
        // Assert size of allLocations is 5 before deletion
        assertEquals(allLocations.size(), 5);
        // Assert that the correct location in correct index before deletion
        assertEquals(allLocations.get(1), new All_Locations("Test City1", "Test State ID1", "Test State Name1", 1, 1));

        result = userService.deleteLocation(index, "username", model, session);

        assertEquals("main", result);
        // Assert size of allLocations is 4 after deletion
        assertEquals(allLocations.size(), 4);
        // Assert that the correct location in correct index after deletion
        assertEquals(allLocations.get(1), new All_Locations("Test City4", "Test State ID4", "Test State Name4", 4, 4));
    }

    @Test
    public void testChangeHome(){
        List<All_Locations> allLocations = new ArrayList<>();
        // Populate allLocations array
        for(int i=0; i<5; i++){
            allLocations.add(new All_Locations("Test City" + i, "Test State ID" + i, "Test State Name" + i, i, i));
        }

        when(session.getAttribute("allLocations")).thenReturn(allLocations);

        // Test index out of bounds
        String result = userService.changeHome(10, "username", model, session);
        assertEquals("redirect:/userView", result);
        // Assert no swap happened
        assertEquals(allLocations.get(0), new All_Locations("Test City0", "Test State ID0", "Test State Name0", 0, 0));

        // Test index 0
        result = userService.changeHome(0, "username", model, session);
        assertEquals("redirect:/userView", result);
        // Assert no swap happened
        assertEquals(allLocations.get(0), new All_Locations("Test City0", "Test State ID0", "Test State Name0", 0, 0));

        // Test valid index
        int index = 4;
        
        result = userService.changeHome(index, "username", model, session);
        assertEquals("redirect:/userView", result);

        // Assert that old home location and new home location swapped places in list
        assertEquals(allLocations.get(0), new All_Locations("Test City4", "Test State ID4", "Test State Name4", 4, 4));
        assertEquals(allLocations.get(4), new All_Locations("Test City0", "Test State ID0", "Test State Name0", 0, 0));
    }
}