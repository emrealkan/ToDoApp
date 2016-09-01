package com.iyzico.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iyzico.todo.components.CustomAuthenticationProvider;

@Controller
public class LoginController {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String getLoginView() {
		return "web/content/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username.trim(),
				password.trim());
		Authentication authenticate = customAuthenticationProvider.authenticate(token);
		if (authenticate != null) {
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			return "web/content/todolist";
		}
		return "redirect:/login?error";
	}

}
