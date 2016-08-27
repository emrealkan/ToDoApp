package com.iyzico.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzico.todo.domain.Role;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
	
	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUserName(username);
		return user;
	}
	
	@Override
	public Boolean createUser(String userName, String email, String password) {
		if(findByEmail(email) == null && findByUsername(userName) == null){
			User user = new User();
			user.setEmail(email);
			user.setName(userName);
			user.setPassword(password);
			user.setRole(Role.USER);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId);
	}
}
