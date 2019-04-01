package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Login;

public class LoginController {
private Login model; 
	
	/**
	 * Set the model
	 * @param model the model to be set
	 */
	
	public void setModel(Login model)
	{
		this.model = model; 
	}
	
	/** 
	 * @param username is the user name that the user name inputs
	 * @param password is the password that the user inputs
	 */
	public void checkCredentials(String username, String password)
	{
		model.setUsername(username);
		model.setPassword(password);
		model.setCredentials();
	}
}
