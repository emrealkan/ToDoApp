package com.iyzico.todo.mobile.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	@RequestMapping(value = "/api/public/getTodos",method = RequestMethod.POST)
	public RestAPIResponse getTodos(@RequestParam("userId") Long userId) {
		
		User user = userService.findById(userId);
		if(user != null){
			Iterable<ToDo> list = toDoService.list(user);
			List<ToDo> targetCollection = new ArrayList<ToDo>();
			addAll(targetCollection, list.iterator());
			return RestAPIResponse.ok(targetCollection);
		}else{
			return RestAPIResponse.error("User does not exist");
		}
		
	}
	
	@RequestMapping(value = "/api/public/createTodo",method = RequestMethod.POST)
	public RestAPIResponse createTodo(@RequestParam("userId") Long userId,
			@RequestParam("title") String title,
			@RequestParam("subTitle") String subTitle, 
			@RequestParam("content") String content, 
			@RequestParam("endDate") String endDate,
			@RequestParam("startDate") String startDate) throws ParseException {
		
		java.sql.Date startTodoDate;
		java.sql.Date endTodoDate;
		try{
			startTodoDate = new java.sql.Date(formatter.parse(startDate).getTime());
			endTodoDate = new java.sql.Date(formatter.parse(endDate).getTime());
		} catch(ParseException e){
			return RestAPIResponse.error("An error occured calculating start-end Date");
		}
		
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
	
	@RequestMapping(value = "/api/public/deleteTodo",method = RequestMethod.POST)
	public RestAPIResponse deleteTodo(@RequestParam("todoId") Long todoId) {
		try{
			toDoService.deleteToDoById(todoId);
		} catch(Exception e){
			return RestAPIResponse.error("An error occured. Please try again");
		}
		return RestAPIResponse.ok("success");
	}
	
	public static <T> void addAll(Collection<T> collection, Iterator<T> iterator) {
	    while (iterator.hasNext()) {
	        collection.add(iterator.next());
	    }
	}
	
}
