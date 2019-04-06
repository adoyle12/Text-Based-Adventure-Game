package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;

/**
 * Model class for gameplay
 * @author adoyle 
 * Only the controller should be allowed to call the set methods
 *
 */
public class Gameplay 
{
	
	//Inventory, Description, Input

	private String input; 		//input from the user
	private ArrayList<String> story = new ArrayList<String>();
	//Inventory inventory; 	//game inventory
	//Description description; 	//Thinking this should be it's own class to have a long & short description
	
	public Gameplay() 
	{

	}
	
	public void setInput (String input)
	{
		this.input = input; 
	}
	
	public void setStory (String input) {
		this.story.add(input);
	}
	
	public String getInput()
	{
		return input; 
	}
	
	public ArrayList<String> getStory(){
		return story;
	}
}
