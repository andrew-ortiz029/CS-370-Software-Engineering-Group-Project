package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String home() {
        return "redirect:/login";
    }
    
    // base website returns the login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // /signup reroutes to the signup page
    @GetMapping("/signup")
    public String signup() {
        return "signup";
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
    }

    // /forgotPassword reroutes to the forgotPassword page
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid @RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            return userService.forgotPassword(email);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("forgotPassError", e.getMessage());
            return "redirect:/forgotPassword";
        }
    }

    // /passwordReset reroutes to the passwordReset page
    @GetMapping("/passwordReset")
    public String passwordReset() {
        return "passwordReset";
    }

    @PostMapping("/passwordReset")
    public String passwordReset(@Valid PasswordResetForm passwordResetForm, RedirectAttributes redirectAttributes) {
        try {
            return userService.passwordReset(passwordResetForm);
        } catch (RuntimeException e) {
            System.out.println("Sending a runtime error");
            redirectAttributes.addFlashAttribute("resetError", e.getMessage());
            return "redirect:/passwordReset";
        }
    }

    // /userView reroutes to the user's main page
    @GetMapping("/userView")
    public String userView(Principal principal, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return userService.userView(username, model, session);
    }

    @PostMapping("/search")
    public String search(@RequestParam String searchLocation, Principal principal, Model model, HttpSession session) {
        if (searchLocation == null || searchLocation.trim().isEmpty()) {
            // Add a modal to display that the search location must be filled out
            // redirectAttributes.addFlashAttribute("error", "Search location must be filled out");
            return "redirect:/userView";
        }
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return userService.search(searchLocation, username, model, session);
    }

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

    @GetMapping("/userView/{index}")
    public String changeLocation(@PathVariable("index") int index, Model model, HttpSession session){ 
        return userService.changeLocation(index, model, session);
    }
}
