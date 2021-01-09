package net.main;

import java.io.Serializable;

public class User implements Serializable {
	String username;
	String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void changeUsername(String username) {
		this.username = username;
	}
	
	public void changePassword(String password) {
		this.password = password;
	}
}
