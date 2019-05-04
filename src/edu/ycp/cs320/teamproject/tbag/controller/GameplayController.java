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
	
	public GameplayController(String username) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		db.setUserFilePath(username); 
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
			
			// ___________________Movement_______________________
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
				
			//__________________Picking up items___________________
			} else if (input.contains("pickup")) {
				if (input.contains("sword")) {
					pickupItem("sword", username);
				} else if (input.contains("dagger")) {
					pickupItem("dagger", username);
				} else if (input.contains("shield")) {
					pickupItem("shield", username);
				} else if (input.contains("green potion")) {
					pickupItem("green potion", username);
				} else if (input.contains("stick")) {
					pickupItem("stick", username);
				} else if (input.contains("potion of redbull")) {
					pickupItem("potion of redbull", username);
				} else if (input.contains("bigger stick")) {
					pickupItem("bigger stick", username);
				} else if (input.contains("pointy peice of paper")) {
					pickupItem("pointy peice of paper", username);
				} else if (input.contains("big boss's broken broom")) {
					pickupItem("big boss's broken broom", username);
				} else if (input.contains("glue in a jar")) {
					pickupItem("glue in a jar", username);
				} else if (input.contains("bag of things")) {
					pickupItem("bag of things", username);
				} else if (input.contains("weaponized math")) {
					pickupItem("weaponized math", username);
				} else {
					System.out.println("Unknown item name");
					db.addUserOutput("Unknown item name");
				}
			//_________________Drop Item_____________________
			} else if(input.contains("drop")) {
				if (input.contains("sword")) {
					dropItem("sword", username);
				} else if (input.contains("dagger")) {
					dropItem("dagger", username);
				} else if (input.contains("shield")) {
					dropItem("shield", username);
				} else if (input.contains("green potion")) {
					dropItem("green potion", username);
				} else if (input.contains("stick")) {
					dropItem("stick", username);
				} else if (input.contains("potion of redbull")) {
					dropItem("potion of redbull", username);
				} else if (input.contains("bigger stick")) {
					dropItem("bigger stick", username);
				} else if (input.contains("pointy peice of paper")) {
					dropItem("pointy peice of paper", username);
				} else if (input.contains("big boss's broken broom")) {
					dropItem("big boss's broken broom", username);
				} else if (input.contains("glue in a jar")) {
					dropItem("glue in a jar", username);
				} else if (input.contains("bag of things")) {
					dropItem("bag of things", username);
				} else if (input.contains("weaponized math")) {
					dropItem("weaponized math", username);
				} else {
					System.out.println("Unknown item name");
					db.addUserOutput("Unknown item name");
				} 
			} else if(input.contains("examine")) {
				if (input.contains("room") || input.contains("location")) {
					examineRoom(username);
				} else {
					System.out.println("Specify what to examine");
				}
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
	
	public int pickupItem(String itemName, String username) {
		Integer returnInt = null;
		
		returnInt = db.pickupItem(itemName, username);
		
		if(returnInt == 0) {
			System.out.println("Could not pickup item");
			db.addUserOutput("Could not pickup item");
		} else if(returnInt == 1) {
			System.out.println("Picked up " + itemName);
			db.addUserOutput("Picked up " + itemName);
		} else if(returnInt == null) {
			System.out.println("Error: Failure occured in itemPickup method");
			db.addUserOutput("Error: Failure occured in itemPickup method");
		}
		
		return returnInt;
	}
	
	public int dropItem(String itemName, String username) {
		Integer returnInt = null;
		
		returnInt = db.dropItem(itemName, username);
		
		if(returnInt == 0) {
			System.out.println("Could not drop item, arms are no good");
			db.addUserOutput("Could not drop item, arms are no good");
		} else if(returnInt == 1) {
			System.out.println("Dropped " + itemName + " on the floor");
			db.addUserOutput("Dropped " + itemName + " on the floor");
		} else if(returnInt == null) {
			System.out.println("Error: Failure occured in dropItem");
			db.addUserOutput("Error: Failure occured in dropItem");
		}
		
		return returnInt;
	}
	
	public String examineRoom(String username) {
		String returnString = null;
		
		int location_id = db.getUserLocation(username);
		
		returnString = db.getLocationDescriptionLong(location_id);
		if(returnString == null) {
			System.out.println("Error: Failure to get room description in examineRoom");
			db.addUserOutput("Error: Failure to get room description in examineRoom");
		} else {
			System.out.println(returnString);
			db.addUserOutput(returnString);
		}
		
		return returnString;
	}
}
