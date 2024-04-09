package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //root website returns the login page
    @GetMapping
    public String login() {
        return "login";
    }
    
    //login page 'login' button performs this function
    @PostMapping
    public String login(@Valid LoginForm loginForm) {
        return userService.userLogin(loginForm);
    }

    //signup page 'signup' button performs this function
    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm) {
        return userService.createUserAccount(signupForm);
    }

    // /signup reroutes to the signup page
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // /forgotPassword reroutes to the forgotPassword page
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    // /userView reroutes to the temp userView page
    @GetMapping("/userView")
    public String userView() {
        return "userView";
    }

    // /userNotFound reroutes to the temp userNotFound page
    @GetMapping("/userNotFound")
    public String userNotFound() {
        return "userNotFound";
    }

    // /passwordNotFound reroutes to the temp passwordNotFound page
    @GetMapping("/passwordNotFound")
    public String passwordNotFound() {
        return "passwordNotFound";
    }
}
