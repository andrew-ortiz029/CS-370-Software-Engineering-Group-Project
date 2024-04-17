package com.c4n.c4n_weather.Users;

import com.c4n.c4n_weather.PasswordHasher;
import com.c4n.c4n_weather.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
           if(!PasswordHasher.verifyPassword(loginForm.getPassword(), optionalUser.get().getPassword())){
                redirectAttributes.addFlashAttribute("loginError", "Password is incorrect.");
                return "redirect:/";
            }
        }
        User user = optionalUser.get();
        return userService.userLogin(loginForm, user);
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
