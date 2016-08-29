package com.iyzico.todo.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.iyzico.todo.domain.CurrentUser;
import com.iyzico.todo.domain.Role;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
    private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        User findByUsername = userService.findByUsername(username);
        
        if(findByUsername != null && findByUsername.getPassword().equalsIgnoreCase(password)){
        	Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority(Role.USER.toString()));
			
			CurrentUser currentUser = new CurrentUser(); 
	    	currentUser.setName(findByUsername.getUserName());
	    	currentUser.setUserRole(Role.USER.toString());
	    	currentUser.setUser(findByUsername);

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.toString()));

			return new UsernamePasswordAuthenticationToken(currentUser, password,grantedAuths);
        }
        
        return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
