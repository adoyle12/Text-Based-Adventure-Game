package edu.ycp.cs320.teamproject.tbag.controller;


import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;

/**
 * Controller for Gameplay
 * @author adoyle 
 *
 */
public class GameplayController 
{
	private IDatabase db = null;
	private Gameplay model; 
	
	public GameplayController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
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

	public void input(String input)
	{
		model.setInput(input);
	}
	
	public ArrayList<String> story(){
		//TODO:List<String> story = db.loadGame;
		ArrayList<String> stories = null;
		
		String story = "Garbage";
		if(story.isEmpty()) {
			System.out.println("No game found");
			return null;
		}
		else {
			//TODO:stories = story;
			stories = model.getStory();
		}	
		return stories;
	}

}
