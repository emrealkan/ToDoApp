package com.iyzico.todo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.service.UserService;

@Component(value="userSignUpFormValidator")
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
        if(!checkEmptyValue(errors, form)){
        	validatePasswords(errors, form);
            checkExistUserName(errors, form.getUserName());
            checkExistUserEmail(errors, form.getEmail());
        }
    }

    private boolean checkEmptyValue(Errors errors, UserSignUpFormModel form) {
    	boolean isError = false;
    	if(form.getEmail().isEmpty()){
			errors.rejectValue("userName", "error.username.not.empty");
			isError = true;
		}
		if(form.getUserName().isEmpty()){
			errors.rejectValue("email", "error.email.not.empty");
			isError = true;
		}
		if(form.getPassword().isEmpty()){
			errors.rejectValue("password", "error.password.not.empty");
			isError = true;
		}
		if(form.getPasswordRepeated().isEmpty()){
			errors.rejectValue("passwordRepeated", "error.repeatpassword.not.empty");
			isError = true;
		}
		return isError;
	}

	private void checkExistUserName(Errors errors, String userName) {
    	if (userService.findByUsername(userName) != null) {
            errors.rejectValue("userName", "error.username.exist");
        }
	}

	private void checkExistUserEmail(Errors errors, String email) {
    	if (userService.findByEmail(email) != null) {
            errors.rejectValue("email", "error.email.exist");
        }
	}

	private void validatePasswords(Errors errors, UserSignUpFormModel form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.rejectValue("passwordRepeated", "error.passwords.notequal");
        }
    }

}
