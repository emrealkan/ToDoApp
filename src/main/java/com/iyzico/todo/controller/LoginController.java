package com.iyzico.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.service.SecurityService;
import com.iyzico.todo.service.UserServiceImpl;

@Controller
public class LoginController {

	@Autowired
    private UserServiceImpl userService;
	
	@Autowired
    private SecurityService securityService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginView() {
		return "web/content/login";
	}
	
	@RequestMapping("/signup")
	public String getSignupView() {
		return "web/content/signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String handleUserCreateForm(@Valid UserSignUpFormModel signupModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup";
		}

		if (userService.createUser(signupModel.getUserName(), signupModel.getEmail(), signupModel.getPassword())) {
			securityService.autologin(signupModel.getUserName(), signupModel.getPassword());
			return "redirect:/user/todolist.html";
		}
		return "signup";
	}

}
