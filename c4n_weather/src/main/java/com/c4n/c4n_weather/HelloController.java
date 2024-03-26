package com.c4n.c4n_weather;


import org.springframework.stereotype.Controller; 
//import org.springframework.web.bind.annotation.RequestMapping; 
//import org.springframework.web.bind.annotation.ResponseBody; 

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/")
public class HelloController {

	@GetMapping("")
	public String index() {
		return "login";
	}
}