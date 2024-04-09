package com.c4n.c4n_weather.Locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.c4n.c4n_weather.UserService;

@Controller
@RequestMapping("/userProfile")
public class LocationController {

    private UserService userService;

    public LocationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    public String userView() {
        return "main";
    }

    
    
}
