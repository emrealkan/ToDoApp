package com.iyzico.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iyzico.todo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	User findByUserName(String userName);
	
}
