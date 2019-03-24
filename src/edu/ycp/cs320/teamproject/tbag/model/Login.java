package edu.ycp.cs320.teamproject.tbag.model;

public class Login {
	
	// Initializing user name and password strings
	private String username;
	private String password;
	
	
	public Login() {
		
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	public Boolean setCheckUsername(String username) {
		if(username == "hello") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean setCheckPassword(String password) {
		if(password == "world") {
			return true;
		}
		else {
			return false;
		}
	}
}
