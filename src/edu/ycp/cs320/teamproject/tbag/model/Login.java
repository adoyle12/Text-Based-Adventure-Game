package edu.ycp.cs320.teamproject.tbag.model;
import java.util.List;

public class Login {
	
	// Initializing user name and password strings
	private String username;
	private String password1;
	private String password2;
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
	

	public void setPassword1 (String password) {
		this.password1 = password;
	}	
	public void setPassword2 (String password) {
		this.password2 = password;
	}	
	public String getPassword1() {
		return this.password1;
	}
	public String getPassword2() {
		return this.password2;
	}

	public void setCredentials()
	{
		if(password1.equals(password2)) {
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
