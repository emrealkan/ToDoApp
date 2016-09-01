package com.iyzico.todo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.model.ToDoFormModel;
import com.iyzico.todo.service.ToDoServiceImpl;
import com.iyzico.todo.validator.ToDoCreateFormValidator;

@Controller
public class ToDoController extends BaseController {

	@Autowired
	private ToDoServiceImpl toDoService;

	@Autowired
	@Qualifier(value = "toDoFormValidator")
	private ToDoCreateFormValidator toDoFormValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.setValidator(toDoFormValidator);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/user/todolist")
	public String getToDoListView(Model model) {

		Iterable<ToDo> list = null;
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			list = toDoService.list(currentUser);
			model.addAttribute("userName", currentUser.getUserName() == null ? "" : currentUser.getUserName());
		}
		model.addAttribute("todos", list);
		return "web/content/todolist";
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/user/createToDo", method = RequestMethod.GET)
	public String getCreateToDoView(Model model) {
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			model.addAttribute("userName", currentUser.getUserName() == null ? "" : currentUser.getUserName());
		}
		model.addAttribute("toDoModel", new ToDoFormModel());
		return "web/content/createToDo";
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/user/createToDo", method = RequestMethod.POST)
	public String createToDo(@Valid @ModelAttribute("toDoModel") ToDoFormModel toDoFormModel,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "web/content/createToDo";
		}

		User currentUser = getCurrentUser();
		if (currentUser != null) {
			ToDo toDo = new ToDo();
			toDo.setEndDate(toDoFormModel.getEndDate());
			toDo.setStartDate(toDoFormModel.getStartDate());
			toDo.setTitle(toDoFormModel.getTitle());
			toDo.setSubTitle(toDoFormModel.getSubTitle());
			toDo.setContent(toDoFormModel.getContent());
			toDo.setUser(currentUser);
			toDoService.createToDo(toDo);
		}
		return "redirect:/user/todolist";
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/user/deleteToDo/{id}", method = RequestMethod.POST)
	public String deleteToDo(@PathVariable("id") Long id) {
		toDoService.deleteToDoById(id);
		return "redirect:/user/todolist";
	}
}
