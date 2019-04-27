package edu.ycp.cs320.teamproject.tbag.controller;


import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
import edu.ycp.cs320.teamproject.tbag.model.Location;

/**
 * Controller for Gameplay
 * @author adoyle 
 *
 */
public class GameplayController 
{
	private IDatabase db;
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
	
	public void setOutput(String output){
		//TODO:List<String> output = db.loadGame;
		/*ArrayList<String> stories = null;
		
		String story = "Garbage";
		if(story.isEmpty()) {
			System.out.println("Not a valid command");
			return null;
		}
		else {
			//TODO:stories = story;
			stories = model.getStory();
		}	
		return stories;
		*/
		
		model.setOutput(output);
	}

	public String getInfoFromDatabase() {
		String input = model.getInput();
		
		// Make input not case sensitive
		input = input.toLowerCase();
		
		String examine = "examine";
		
		if(input.contains(examine)) {
			//String returnString = db.getRoomDescription();
		}
		return input;
	}
	
	// Get next location
	public int getNextLocation(int location_id) {
		return location_id ++;
	}
		
	public String getLocationDescriptionLong(int location_id) {
		Location location = db.getLocationDescriptionLong(getNextLocation(location_id));
			
		String returnString = location.getLongDescription();
		
		return returnString;
	}
	
	public int getCurrentLocation() {
		return db.getItemLocationID("Dagger");
	}
}
