package com.iyzico.todo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.iyzico.todo.domain.User;
import com.iyzico.todo.service.UserService;

public abstract class BaseController {

	@Autowired
	private UserService userService;
	
	public enum SessionItem{
		_CURRENT_CONTACT_
	}
	
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User findByUsername = userService.findByUsername(userName);
		return findByUsername;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getFromSession(HttpServletRequest request, SessionItem sessionItem, Class<T> t){
		Object o = request.getSession().getAttribute(sessionItem.name());
		return (T)o;
	}

}
