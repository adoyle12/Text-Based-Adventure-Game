package edu.ycp.cs320.teamproject.tbag.model;
import java.util.List;

public class Login {
	
	// Initializing user name and password strings
	private String username;
	private String password;
	private Boolean credentials;
	
	//Constructor
	public Login() {
		
	}
	
	//Getters and setters for user name and password
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
		return this.password;
	}

	public void setCredentials()
	{
		if (username.equals("hello") && password.equals("world"))
		{
			credentials = true; 
		}
		else {
			credentials = false;
		}
	}
	
	public Boolean getCredentials()
	{
		return credentials; 
	}
}
