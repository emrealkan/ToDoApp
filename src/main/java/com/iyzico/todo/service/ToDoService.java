package com.iyzico.todo.service;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;

public interface ToDoService {
	
	Iterable<ToDo> list(User user);

	void createToDo(ToDo todo);

	Boolean deleteToDo(ToDo todo, User user);

}
