package com.iyzico.todo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iyzico.todo.model.ToDoFormModel;

@Component(value = "toDoFormValidator")
public class ToDoCreateFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ToDoFormModel.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ToDoFormModel form = (ToDoFormModel) target;
		checkEmptyValue(errors, form);
	}

	private void checkEmptyValue(Errors errors, ToDoFormModel form) {
		if (form.getTitle().isEmpty()) {
			errors.rejectValue("title", "error.title.not.empty");
		}
		if (form.getSubTitle().isEmpty()) {
			errors.rejectValue("subTitle", "error.subtitle.not.empty");
		}
		if (form.getContent().isEmpty()) {
			errors.rejectValue("content", "error.content.not.empty");
		}
	}
}
