package com.iyzico.todo.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')").anyRequest().fullyAuthenticated().and().formLogin()
			.loginPage("/login").failureUrl("/login?error").usernameParameter("username").passwordParameter("password")
			.passwordParameter("password").defaultSuccessUrl("/user/todolist").permitAll().and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("remember-me")
			.logoutSuccessUrl("/login").permitAll().and().rememberMe();
	}
	
	/*
	 * Added for prevent error "Could not verify the provided CSRF token" rest api's
	 *  http://stackoverflow.com/questions/30366405/how-to-disable-spring-security-for-particular-url(non-Javadoc)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/api/**");
	}
	
}
