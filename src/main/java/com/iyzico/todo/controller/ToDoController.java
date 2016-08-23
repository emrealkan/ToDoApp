package com.iyzico.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.model.ToDoFormModel;
import com.iyzico.todo.service.ToDoServiceImpl;

@Controller
public class ToDoController extends BaseController{
	
	@Autowired
    private ToDoServiceImpl toDoService;
	
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/user/todolist")
	public String getToDoListView(Model model) {
		
		Iterable<ToDo> list = null;
		User currentUser = getCurrentUser();
		if(currentUser != null){
			list = toDoService.list(currentUser);	
		}
		model.addAttribute("todos", list);
		return "web/content/todolist";
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value="/user/createToDo", method=RequestMethod.GET)
	public String getCreateToDoView(Model model) {
		model.addAttribute("toDoModel",new ToDoFormModel());
		return "web/content/createToDo";
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value="/user/createToDo", method=RequestMethod.POST)
    public String createToDo(@Valid ToDoFormModel toDoModel, BindingResult bindingResult, Model model) {
        
		if (bindingResult.hasErrors()) {
			return "redirect:createToDo.html";
		}
		
		User currentUser = getCurrentUser();
		
		if(currentUser != null){
			ToDo toDo = new ToDo();
			toDo.setEndDate(toDoModel.getEndDate());
			toDo.setStartDate(toDoModel.getStartDate());
			toDo.setTitle(toDoModel.getTitle());
			toDo.setSubTitle(toDoModel.getSubTitle());
			toDo.setContent(toDoModel.getContent());
			toDo.setUser(currentUser);
			toDoService.createToDo(toDo);
			return "redirect:todolist.html";
		}
		
		return "redirect:createToDo.html";
        
    }
}
