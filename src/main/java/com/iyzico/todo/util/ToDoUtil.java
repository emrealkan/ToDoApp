package com.iyzico.todo.util;

import org.apache.commons.validator.EmailValidator;

public class ToDoUtil {

	public static boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}
