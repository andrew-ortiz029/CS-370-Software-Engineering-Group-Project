package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // root website reroutes to the login page
    @GetMapping("")
    public String home() {
        return "redirect:/login";
    }
    
    // /login returns the login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // /signup returns the signup page
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // /signup post method creates a new user account using UserService
    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, RedirectAttributes redirectAttributes) {
        try{
            return userService.createUserAccount(signupForm);
        } 
        catch (RuntimeException e) {
            // redirectAttributes is used to display a modal message on the signup page
            redirectAttributes.addFlashAttribute("signupError", e.getMessage());
            return "redirect:/signup";
        }
    }

    // /forgotPassword returns the forgotPassword page
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    // /forgotPassword post method send an email to the user using UserService
    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid @RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            return userService.forgotPassword(email);
        } catch (RuntimeException e) {
            // redirectAttributes is used to display a modal message on the forgotPassword page
            redirectAttributes.addFlashAttribute("forgotPassError", e.getMessage());
            return "redirect:/forgotPassword";
        }
    }

    // /passwordReset returns the passwordReset page
    @GetMapping("/passwordReset")
    public String passwordReset() {
        return "passwordReset";
    }

    // /passwordReset post method resets the user's password using UserService
    @PostMapping("/passwordReset")
    public String passwordReset(@Valid PasswordResetForm passwordResetForm, RedirectAttributes redirectAttributes) {
        try {
            return userService.passwordReset(passwordResetForm);
        } catch (RuntimeException e) {
            // redirectAttributes is used to display a modal message on the passwordReset page
            redirectAttributes.addFlashAttribute("resetError", e.getMessage());
            return "redirect:/passwordReset";
        }
    }

    // /userView displays the user's home page using UserController
    @GetMapping("/userView")
    public String userView(Principal principal, Model model, HttpSession session) {
        String username = "";
        // get the username of the current user based on the authentication token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return userService.userView(username, model, session);
    }
    
    // /search GetMapping displays the same current page
    @GetMapping("/search")
    public String search(Model model, HttpSession session) {
        Object weather = session.getAttribute("weather");
        if (weather != null) {
            model.addAttribute("weather", weather);
        }
        Object CityState = session.getAttribute("CityState");
        if (CityState != null) {
            model.addAttribute("CityState", CityState);
        }
        return "main";
    }

    // /search PostMapping searches for a location using UserService
    @PostMapping("/search")
    public String search(@RequestParam String searchLocation, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // if the search location is empty, redirectAttributes is used to display a modal message on the userView page
        if (searchLocation == null || searchLocation.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("invalidLocation", "Search location must not be empty");
            return "redirect:/userView";
        }
        String username = "";
        // get the username of the current user based on the authentication token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        try {
            return userService.search(searchLocation, username, model, session);
        } catch (RuntimeException e) {
            // if the location is not valid, redirectAttributes is used to display a modal message on the userView page
            redirectAttributes.addFlashAttribute("invalidLocation", e.getMessage());
            return "redirect:/userView";
        }
    }

    // /userView/{index} changes the user's location using UserService
    @GetMapping("/userView/{index}")
    public String changeLocation(@PathVariable("index") int index, Model model, HttpSession session){ 
        return userService.changeLocation(index, model, session);
    }

    // /delete/{index} deletes the user's selected location using UserService
    @GetMapping("/delete/{index}")
    public String deleteLocation(@PathVariable("index") int index, Model model, HttpSession session){
        String username = "";
        // get the username of the current user based on the authentication token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return userService.deleteLocation(index, username, model, session);
    }

    // /favorite/{index} changes the user's home location using UserService
    @GetMapping("/favorite/{index}")
    public String changeHome(@PathVariable("index") int index, Model model, HttpSession session){
        String username = "";
        // get the username of the current user based on the authentication token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return userService.changeHome(index, username, model, session);
    }
}
