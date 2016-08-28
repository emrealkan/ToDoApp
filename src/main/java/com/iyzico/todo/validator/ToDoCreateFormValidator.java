package com.iyzico.todo.validator;

import java.sql.Date;

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
		if(!checkEmptyValue(errors, form)){
			checkDateValus(errors, form.getStartDate(),form.getEndDate());
		}
	}

	private void checkDateValus(Errors errors, Date startDate, Date endDate) {
		if(startDate.after(endDate)){
			errors.rejectValue("startDate", "error.startDate.not.greater");
		}
	}

	private boolean checkEmptyValue(Errors errors, ToDoFormModel form) {
		boolean isError = false;
		if (form.getTitle().isEmpty()) {
			errors.rejectValue("title", "error.title.not.empty");
			isError = true;
		}
		if (form.getSubTitle().isEmpty()) {
			errors.rejectValue("subTitle", "error.subtitle.not.empty");
			isError = true;
		}
		if (form.getContent().isEmpty()) {
			errors.rejectValue("content", "error.content.not.empty");
			isError = true;
		}
		if(form.getStartDate() == null) {
			errors.rejectValue("startDate", "error.startDate.not.empty");
			isError = true;
		}
		if(form.getEndDate() == null) {
			errors.rejectValue("endDate", "error.endDate.not.empty");
			isError = true;
		}
		return isError;
	}
}
