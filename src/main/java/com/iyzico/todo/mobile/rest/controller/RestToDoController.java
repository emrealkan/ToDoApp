package com.iyzico.todo.mobile.rest.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.mobile.rest.components.RestAPIResponse;
import com.iyzico.todo.service.ToDoServiceImpl;
import com.iyzico.todo.service.UserServiceImpl;

@RestController
public class RestToDoController {
	
	@Autowired
    private ToDoServiceImpl toDoService;
	
	@Autowired
	private UserServiceImpl userService;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
	
	@RequestMapping(value = "/api/public/getTodos",method = RequestMethod.GET)
	public RestAPIResponse getTodos(@RequestParam("userId") Long userId) {
		
		User user = userService.findById(userId);
		if(user != null){
			Iterable<ToDo> list = toDoService.list(user);
			return RestAPIResponse.ok(list);
		}else{
			return RestAPIResponse.error("User does not exist");
		}
		
	}
	
	@RequestMapping(value = "/api/public/createTodo",method = RequestMethod.POST)
	public RestAPIResponse createTodo(@RequestParam("userId") Long userId,
			@RequestParam("title") String title,
			@RequestParam("subTitle") String subTitle, 
			@RequestParam("subTitle") String content, 
			@RequestParam("endDate") String endDate,
			@RequestParam("startDate") String startDate) {
		
		Date startTodoDate = convertFrom(LocalDate.parse(startDate, formatter));
		Date endTodoDate = convertFrom(LocalDate.parse(endDate, formatter));
		
		
		if (startTodoDate.after(endTodoDate)){
			return RestAPIResponse.error("End date must be after start date");
		}
		
		User user = userService.findById(userId);
		if(user != null){
			ToDo todo = new ToDo();
			todo.setEndDate(endTodoDate);
			todo.setStartDate(startTodoDate);
			todo.setTitle(title);
			todo.setSubTitle(subTitle);
			todo.setContent(content);
			todo.setUser(user);
			toDoService.createToDo(todo);
			return RestAPIResponse.ok("success");
		}else{
			return RestAPIResponse.error("Error check your informations");
		}
		
	}
	
	@RequestMapping(value = "/api/public/deleteTodo",method = RequestMethod.DELETE)
	public RestAPIResponse deleteTodo(@RequestParam("todoId") Long todoId) {
		try{
			toDoService.deleteToDoById(todoId);
		} catch(Exception e){
			return RestAPIResponse.error("An error occured. Please try again");
		}
		return RestAPIResponse.ok("success");
	}
	
	public Date convertFrom(LocalDate date) {
	    return java.sql.Date.valueOf(date);
	}
}
