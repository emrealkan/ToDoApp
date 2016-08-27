package com.iyzico.todo.mobile.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iyzico.todo.domain.User;
import com.iyzico.todo.mobile.rest.components.RestAPIResponse;
import com.iyzico.todo.service.UserServiceImpl;

@RestController
public class RestLoginController {

	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(value = "/api/public/login",method = RequestMethod.POST)
	public RestAPIResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
		
		User user = userService.findByUsername(username.trim());
		if (user == null) {
			return RestAPIResponse.error("Your username not found. Please register.");
		} else if (user != null && user.getPassword().equalsIgnoreCase(password)) {
			return RestAPIResponse.ok(user);
		} else {
			return RestAPIResponse.error("Your username and password are not matched");
		}
		
	}
}
