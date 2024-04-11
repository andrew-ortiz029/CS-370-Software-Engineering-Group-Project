package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.PasswordHasher;
import com.c4n.c4n_weather.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // root website returns the login page
    @GetMapping
    public String login() {
        return "login";
    }

    // login page 'login' button performs this function
    @PostMapping
    public String login(@Valid LoginForm loginForm, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userRepository.findByUsername(loginForm.getUsername());
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("loginError", "Email is incorrect or does not exist.");
            return "redirect:/";
        } 
        else {
            User user = optionalUser.get();
           if(!PasswordHasher.verifyPassword(loginForm.getPassword(), user.getPassword())){
                redirectAttributes.addFlashAttribute("loginError", "Password is incorrect.");
                return "redirect:/";
            }
        }
        return userService.userLogin(loginForm);
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, RedirectAttributes redirectAttributes) {
    try{
        return userService.createUserAccount(signupForm);
    } 
    catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("signupError", e.getMessage());
        return "redirect:/signup";
    }
    //return userService.createUserAccount(signupForm);
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
