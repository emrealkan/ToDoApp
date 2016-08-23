package com.iyzico.todo.service;

import com.iyzico.todo.domain.CurrentUser;

public interface CurrentUserService {
	 boolean canAccessUser(CurrentUser currentUser, Long userId);
}
