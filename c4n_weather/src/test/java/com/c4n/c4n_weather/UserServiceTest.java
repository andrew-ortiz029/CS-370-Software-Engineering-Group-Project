package com.c4n.c4n_weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.c4n.c4n_weather.Users.*;
import com.c4n.c4n_weather.Locations.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
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
    Weather weather;

    @InjectMocks
    private UserService userService;

    @InjectMocks 
    private UserController userController;

    //email in use
    //name is only letters
    //email is valid
    //password is between 5 and 60 characters
    @Test
    public void testCreateUserAccount() {
        // Set up SignupForm with valid signup credentials
        SignupForm signupForm = new SignupForm("test@Username.com", "testPassword", "testName", "testCity", "testState");
        // Set up User with valid credentials
        User user = new User(signupForm.getUsername(), signupForm.getPassword(), signupForm.getName());
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
        when(all_LocationsRepository.getLocationByCityStateName("testCity", "testState")).thenReturn(Optional.of(location));

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
    void testLogin(){
        // Set up LoginForm with valid login credentials
        LoginForm loginForm = new LoginForm("test@Username.com", "testPassword");
        // Set up User with valid credentials
        User user = new User(loginForm.getUsername(), loginForm.getPassword(), "testName");
        // Set up LoginForm with invalid email
        LoginForm loginFormIncorrectEmail = new LoginForm("userName", "testPassword");
        // Set up LoginForm with invalid password
        LoginForm loginFormIncorrectPassword = new LoginForm("test@Username.com", "password");

        // test getWeather()
        when(weatherService.getWeatherData("1.1111", "1.1111")).thenReturn(weather);

        // Create a new Current object
        Weather.Current current = new Weather.Current();
        current.setTemp(20.0);
        current.setFeels_like(25.0);

        //set up weather.toString()
        when(weather.toString()).thenReturn("Mock weather data");

        // test valid login
        when(userRepository.findByUsername(loginForm.getUsername())).thenReturn(Optional.of(user));
        mockStatic(PasswordHasher.class);
        when(PasswordHasher.verifyPassword(loginForm.getPassword(), user.getPassword())).thenReturn(true);
        when(locationRepository.getUserHome(loginForm.getUsername())).thenReturn(Optional.of(new Location(1.1111, 1.1111, loginForm.getUsername(), true)));

        // test incorrect email
        when(userRepository.findByUsername(loginFormIncorrectEmail.getUsername())).thenReturn(Optional.empty());
        // test incorrect password
        when(PasswordHasher.verifyPassword(loginFormIncorrectPassword.getPassword(), user.getPassword())).thenReturn(false);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        // test incorrect email
        String result = userController.login(loginFormIncorrectEmail, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("loginError"));
        assertEquals("Email is incorrect or does not exist.", redirectAttributes.getFlashAttributes().get("loginError"));

        // test incorrect password
        result = userController.login(loginFormIncorrectPassword, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("loginError"));
        assertEquals("Password is incorrect.", redirectAttributes.getFlashAttributes().get("loginError"));

        // test valid login
        result = userService.userLogin(loginForm, user);
        assertEquals("redirect:/userView", result);
    }
}