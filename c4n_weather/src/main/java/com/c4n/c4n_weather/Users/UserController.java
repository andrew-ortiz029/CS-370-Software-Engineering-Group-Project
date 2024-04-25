package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.Optional;

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

    // base website returns the login page
    @GetMapping("login")
    public String login() {
        return "login";
    }

    // login page 'login' button performs this function
    // using prg pattern to avoid resubmission of form data 
    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, RedirectAttributes redirectAttributes,  HttpServletRequest request) {
        try {
            request.login(loginForm.getUsername(), loginForm.getPassword());
        } 
        /*
         * Modal does not work anymore, can try to figure out later
         */
        // catch (UsernameNotFoundException e){
        //     System.out.println("\n\n\nusername not found from controller\n\n\n");
        //     redirectAttributes.addFlashAttribute("loginError", "Email does not exist or is incorrect");
        // } catch (BadCredentialsException e){
        //     System.out.println("\n\n\npassword not found\n\n\n");
        //     redirectAttributes.addFlashAttribute("loginError", "Password is incorrect");
        // } 
        catch (ServletException e) {
            redirectAttributes.addFlashAttribute("loginError", "An error occurred");
            return "redirect:/login";
        }
        Optional<User> optionalUser = userRepository.findByUsername(loginForm.getUsername());
        User user = optionalUser.get();
        return userService.userLogin(loginForm, user, redirectAttributes);
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
    //return userService.createUserAccount(signupForm);
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

    // /userView reroutes to the temp userView page
    @GetMapping("/userView")
    public String userView() {
        return "main";
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
}
