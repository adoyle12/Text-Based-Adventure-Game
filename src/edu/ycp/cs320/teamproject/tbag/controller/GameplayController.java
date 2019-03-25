package edu.ycp.cs320.teamproject.tbag.controller;


import java.util.ArrayList;

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
	public void setPage(String input) {
		model.setInput(input);
		model.setStory(input);
	}
	
	public ArrayList<String> getStuff() {
		return model.getStory();
	}

}
