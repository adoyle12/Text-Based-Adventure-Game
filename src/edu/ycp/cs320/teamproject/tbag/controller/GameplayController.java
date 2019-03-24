package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Gameplay;

/**
 * Controller for Gameplay
 * @author adoyle 
 *
 */
public class GameplayController 
{
	private Gameplay model; 
	
	/**
	 * Set the model
	 * @param model the model to be set
	 */
	
	public void setModel(Gameplay model)
	{
		this.model = model; 
	}
	
	/**
	 * Setting the input from the user
	 * @param input the input from user
	 */
	public void setInput(String input)
	{
		model.setInput(input);
	}
	
	public String getInput()
	{
		return model.getInput();
	}

}
