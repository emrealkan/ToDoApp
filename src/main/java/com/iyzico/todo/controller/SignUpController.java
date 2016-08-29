package com.iyzico.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.service.SecurityService;
import com.iyzico.todo.service.UserServiceImpl;
import com.iyzico.todo.validator.UserCreateFormValidator;

@Controller
public class SignUpController {
	
	@Autowired
    private UserServiceImpl userService;
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
	@Qualifier(value = "userSignUpFormValidator")
	private UserCreateFormValidator userSignUpFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userSignUpFormValidator);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String getSignupView(Model model) {
		model.addAttribute("signupModel",new UserSignUpFormModel());
		return "web/content/signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String handleUserCreateForm(@Valid @ModelAttribute("signupModel") UserSignUpFormModel signupModel, BindingResult bindingResult,RedirectAttributes attr) {
		if (bindingResult.hasErrors()) {
		    return "web/content/signup";
		}
		if (userService.createUser(signupModel.getUserName().trim(), signupModel.getEmail().trim(), signupModel.getPassword().trim())) {
			securityService.autologin(signupModel.getUserName().trim(), signupModel.getPassword().trim());
			return "redirect:/user/todolist.html";
		}
		return "web/content/signup";
	}
}
