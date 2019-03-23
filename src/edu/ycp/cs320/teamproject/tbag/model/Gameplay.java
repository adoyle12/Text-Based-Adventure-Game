package edu.ycp.cs320.teamproject.tbag.model;

/**
 * Model class for gameplay
 * @author adoyle 
 * Only the controller should be allowed to call the set methods
 *
 */
public class Gameplay 
{
	
	//Inventory, Description, Input

	String input; 		//input from the user
	//Inventory inventory; 	//game inventory
	//Description description; 	//Thinking this should be it's own class to have a long & short description
	
	
	
	public Gameplay() 
	{
		
	}
	
	public void setInput (String input)
	{
		this.input = input; 
	}
	
	public String getInput()
	{
		return input; 
	}
}
