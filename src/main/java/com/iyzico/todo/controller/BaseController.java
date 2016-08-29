package com.iyzico.todo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.iyzico.todo.domain.CurrentUser;
import com.iyzico.todo.domain.User;

public abstract class BaseController {
	
	public enum SessionItem{
		_CURRENT_CONTACT_
	}
	
	public User getCurrentUser() {
		CurrentUser currentUser= (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser != null){
			User user = currentUser.getUser();
			return user;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getFromSession(HttpServletRequest request, SessionItem sessionItem, Class<T> t){
		Object o = request.getSession().getAttribute(sessionItem.name());
		return (T)o;
	}

}
