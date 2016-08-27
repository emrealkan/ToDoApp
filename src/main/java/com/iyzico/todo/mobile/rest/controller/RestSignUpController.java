package com.iyzico.todo.mobile.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iyzico.todo.domain.User;
import com.iyzico.todo.mobile.rest.components.RestAPIResponse;
import com.iyzico.todo.service.UserServiceImpl;

@RestController
public class RestSignUpController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(value = "/api/public/signup", method = RequestMethod.POST)
	public RestAPIResponse login(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email) {

		if (userService.createUser(username.trim(), email.trim(), password.trim())) {
			User user = userService.findByUsername(username);
			return RestAPIResponse.ok(user);
		} else {
			return RestAPIResponse.error("Email and username are exist.");
		}

	}
	
}
