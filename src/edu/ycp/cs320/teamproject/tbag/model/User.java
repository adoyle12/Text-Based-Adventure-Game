package edu.ycp.cs320.teamproject.tbag.model;

public class User extends Agent {
	
	// Initializing user name and password strings
	private String username;
	private String JSPPassword;
	private String DBPassword;
	private Boolean credentials;
	private int locationID;
	
	//Constructor
	public User() {
		
	}
	
	//Getters and setters for user name and password
	public void setUsername (String username) {
		this.username = username;
	}	
	
	public String getUsername() {
		return username;
	}
	
	public void setJSPPassword (String password) {
		this.JSPPassword = password;
	}	
	public void setDBPassword (String password) {
		this.DBPassword = password;
	}	
	public String getJSPPassword() {
		return this.JSPPassword;
	}
	public String getDBPassword() {
		return this.DBPassword;
	}

	public void setCredentials()
	{
		if(JSPPassword.equals(DBPassword)) {
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
	
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	
	public int getLocationID() {
		return locationID;
	}
}
