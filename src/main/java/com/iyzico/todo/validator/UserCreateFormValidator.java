package com.iyzico.todo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.service.UserService;

@Component
public class UserCreateFormValidator implements Validator {

	@Autowired
	private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserSignUpFormModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSignUpFormModel form = (UserSignUpFormModel) target;
        validatePasswords(errors, form);
        checkExistUserEmail(errors, form.getEmail());
    }

    private void checkExistUserEmail(Errors errors, String email) {
    	if (userService.findByEmail(email) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
	}

	private void validatePasswords(Errors errors, UserSignUpFormModel form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.rejectValue("passwordRepeated", "Passwords do not match");
        }
    }

}
