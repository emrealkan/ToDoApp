package com.iyzico.todo.service;

import com.iyzico.todo.domain.User;

public interface UserService {
	
	User findById(Long userId);
	User findByEmail(String email);
	User findByUsername(String username);
	Boolean createUser(String username, String email, String password);

}
