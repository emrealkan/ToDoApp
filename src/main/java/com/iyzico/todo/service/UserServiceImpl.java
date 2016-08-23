package com.iyzico.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public User findByEmailAndPassword(String email, String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if(user == null){
			return null;
		}
		return user;
	}

	@Override
	public Boolean createUser(String userName, String email, String password) {
		User user = findByEmail(email);
		if(user == null){
			user = new User();
			user.setEmail(email);
			user.setName(userName);
			user.setPassword(password);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	
	
	
	
    
    
}
