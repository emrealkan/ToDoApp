package com.iyzico.todo.domain;

import java.io.Serializable;
import java.security.Principal;

public class CurrentUser implements Principal, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String name;
	private String userRole;
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }

}
