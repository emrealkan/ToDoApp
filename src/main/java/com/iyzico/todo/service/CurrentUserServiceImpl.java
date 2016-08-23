package com.iyzico.todo.service;

import org.springframework.stereotype.Service;

import com.iyzico.todo.domain.CurrentUser;
import com.iyzico.todo.domain.Role;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }

}
