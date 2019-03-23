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
	 * Adding three numbers together. Have to set all three first and use setAdd().
	 * There might be some overkill here with all the setters and getters but I did it this way to make sure the JSPs could 
	 * call directly from the model 
	 * @param first the first number to add
	 * @param second the second number to add
	 * @param third the third number to add
	 */
	public Boolean checkID(String username, String password)
	{
		model.setUsername(username);
		model.setPassword(password);
		if(model.setCheckUsername(model.getUsername()) == true && model.setCheckPassword(model.getPassword()) == true) {
			return true;
		}
		else {
			return false;
		}
	}
}
