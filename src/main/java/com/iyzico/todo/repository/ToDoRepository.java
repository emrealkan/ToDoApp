package com.iyzico.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long>{
	
	Iterable<ToDo> findAllByUser(User user);
	
}
