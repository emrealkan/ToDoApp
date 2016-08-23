package com.iyzico.todo.service;

import com.iyzico.todo.domain.User;

public interface UserService {

	User findByEmail(String email);

	User findByUsername(String username);

	User findByEmailAndPassword(String email, String password);

	Boolean createUser(String username, String email, String password);

}
