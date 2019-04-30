package edu.ycp.cs320.teamproject.tbag.controller;


import java.util.ArrayList;
import java.util.List;

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
	private ArrayList<String> output;
	
	public GameplayController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public String gameLogic(String input, String username) {
		String errorMessage = null;
		
		//check for errors in the form data before using is in a calculation
		if (input == null) 
		{
			errorMessage = "Please select a command.";
			System.out.println(errorMessage);
			db.addUserOutput(errorMessage);
			output();
		}
		else 
		{
			//otherwise, data is good, do the calculation using controller
			input = input.toLowerCase();
			input(input);
			
			// _________Movement_____________
			if(input.contains("move")) {
				if(input.contains("north")) {
					moveTo(username, 0);
				}
				else if(input.contains("south")) {
					moveTo(username, 1);
				}
				else if(input.contains("east")) {
					moveTo(username, 2);
				} 
				else if(input.contains("west")){
					moveTo(username, 3);
				} else {
					System.out.println("Unknown direction");
					db.addUserOutput("Unknown direction");
				}
			} else if (input.contains("map")) {
				// This prints out the locationID's and the jointLocations
				displayMap();
			} else {
				System.out.println("Unknown command");
				db.addUserOutput("Unknown command");
			}
		}		
		output();
		return errorMessage;
	}
	
	public void setModel(Gameplay model)
	{
		this.model = model; 
	}

	public void input(String input)
	{
		db.addUserInput(input);
		model.setInput(input);
	}
	
	public void output(){
		output = db.getInputs();
		model.setOutput(output);
	}
	
	public void displayMap() {
		model.displayMap();
	}
	
	public int moveTo(String username, int direction) {
		int currentLocation = db.getUserLocation(username);
		int nextLocation = -1;
		if(direction == 0) {
			nextLocation = db.getJointLocationNorth(currentLocation);
		} else if(direction == 1) {
			nextLocation = db.getJointLocationSouth(currentLocation);
		} else if(direction == 2) {
			nextLocation = db.getJointLocationEast(currentLocation);
		} else {
			nextLocation = db.getJointLocationWest(currentLocation);
		}
		if(currentLocation == nextLocation) {
			System.out.println("Can't move that way");
			
			ArrayList<String> output = new ArrayList<String>();
			output.add("Can't move that way");
			db.addUserOutput("Can't move that way");
			model.addOutput(output);
		} else {
			db.setUserLocation(nextLocation, username);
			String roomDescription = null;
			if(db.getPlayerHasBeen(nextLocation, username) == 0) {
				roomDescription = db.getLocationDescriptionLong(nextLocation);
				db.setPlayerHasBeen(nextLocation, username, 1);
			} else if(db.getPlayerHasBeen(nextLocation, username) == 1) {
				roomDescription = db.getLocationDescriptionShort(nextLocation);
			}
			db.addUserOutput(roomDescription);
			
			// This adds the output into the model
			// Do we need the output in the model though?
			ArrayList<String> output = new ArrayList<String>();
			output.add(roomDescription);
			model.addOutput(output);
			
		}
		if(nextLocation == -1) {
			System.out.println("Movement failed");
		}
		
		return nextLocation;
	}
}
