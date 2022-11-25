package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.request.UserRegistration;
import com.example.demo.services.UserRegistrationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@PostMapping("/registration")
	public String register(@RequestBody UserRegistration request) {
		
		return userRegistrationService.register(request);
		
	}
	
	@GetMapping("/registration/confirm")
	public String confirm(@RequestParam("token") String token) {
		return userRegistrationService.confirmToken(token);
	};
	
}
