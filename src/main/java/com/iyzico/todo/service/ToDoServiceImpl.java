package com.iyzico.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.repository.ToDoRepository;

@Service
public class ToDoServiceImpl implements ToDoService{
	
	@Autowired
    private ToDoRepository todoRepository;
	
	@Override
	public Iterable<ToDo> list(User user) {
		return todoRepository.findAllByUser(user);
	}

	@Override
	public void createToDo(ToDo todo) {
		todoRepository.save(todo);
	}

	@Override
	public void deleteToDoById(long id) {
		todoRepository.delete(id);
	}

	@Override
	public ToDo findOne(long id) {
		ToDo todo = todoRepository.findOne(id);
		return todo;
	}

}
