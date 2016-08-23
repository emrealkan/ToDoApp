package com.iyzico.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
	        .antMatchers("/**").permitAll()
	        .antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')")
	        .anyRequest().fullyAuthenticated()
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .failureUrl("/login?error")
	        .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/user/todolist")	
	        .permitAll()
	        .and()
	        .logout()
	        .logoutUrl("/logout")
	        .deleteCookies("remember-me")
	        .logoutSuccessUrl("/")
	        .permitAll()
	        .and()
	        .rememberMe();
	}
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }
	
}