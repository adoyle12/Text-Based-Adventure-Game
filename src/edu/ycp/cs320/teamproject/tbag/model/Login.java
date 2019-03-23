package edu.ycp.cs320.teamproject.tbag.model;

public class Login {
	
	// Initializing username and password strings
	private String username;
	private String password;
	
	
	public Login() {
		
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
