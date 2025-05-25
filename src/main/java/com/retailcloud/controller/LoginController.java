package com.retailcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retailcloud.dto.LoginResponse;
import com.retailcloud.dto.RegisterRequest;
import com.retailcloud.model.Users;
import com.retailcloud.service.UserService;

@RestController
public class LoginController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public Users register(@RequestBody RegisterRequest user) {
		return service.register(user);

	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody RegisterRequest user) {

		return service.verify(user);
	}

}
