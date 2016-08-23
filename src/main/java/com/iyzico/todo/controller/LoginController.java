package com.iyzico.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.service.UserServiceImpl;

@Controller
public class LoginController {

	@Autowired
    private UserServiceImpl userService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginView() {
		return "web/content/login";
	}
	
	@RequestMapping("/signup")
	public String getSignupView() {
		return "web/content/signup";
	}
	
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserSignUpFormModel form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // failed validation
            return "user_create";
        }
        try {
        	userService.createUser(form.getUserName(), form.getEmail(), form.getPassword());
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            bindingResult.reject("email.exists", "Email already exists");
            return "web/content/login";
        }
        // ok, redirect
        return "redirect:/web/content/todolist";
    }

}
