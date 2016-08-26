package com.iyzico.todo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iyzico.todo.domain.CurrentUser;
import com.iyzico.todo.domain.Role;
import com.iyzico.todo.domain.User;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;


    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autologin(String username, String password) {
    	
    	Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority(Role.USER.toString()));
		
    	CurrentUser currentUser = new CurrentUser(); 
    	User user = userService.findByUsername(username);
    	currentUser.setName(username);
    	currentUser.setUserRole(Role.USER.toString());
    	currentUser.setUser(user);
    	
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(currentUser, password, grantedAuths);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

}
