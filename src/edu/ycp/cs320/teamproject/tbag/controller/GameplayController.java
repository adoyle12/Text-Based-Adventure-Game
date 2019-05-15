package edu.ycp.cs320.teamproject.tbag.controller;


import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
import edu.ycp.cs320.teamproject.tbag.model.Item;


public class GameplayController 
{
	private IDatabase db;
	private Gameplay model; 
	private ArrayList<String> output;
	private int userLocation;
	private List<Item> itemsInRoom;
	private List<Item> usersInventory; 
	private int userScore; 
	private int userHealth; 
	
	
	public GameplayController(String username, Boolean newGame) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		db.setUserFilePath(username);
		if (!newGame)
		{
			userLocation = db.getUserLocation();
			itemsInRoom = db.getItemsInLocation(userLocation);
			usersInventory = db.getItemsInLocation(0); 
			userScore = db.getUserScore(); 
			userHealth = db.getUserHealth(); 
		}
		else
		{
			db.resetGame();	//OH FUCK	
		}
		
	}
	

	
	public void gameLogic(String rawInput) {
		db.addToCommands(rawInput); 
		model.setInput(rawInput);
		String input = rawInput.toLowerCase();
		db.setUserScore(1);
		
		// ___________________Movement_______________________
		if(input.contains("move") || input.contains("go ")) {
			if(input.contains("north")) {
				moveTo(0);
			}
			else if(input.contains("south")) {
				moveTo(1);
			}
			else if(input.contains("east")) {
				moveTo(2);
			} 
			else if(input.contains("west")){
				moveTo(3);
			}
			else if(input.contains("up")){
				moveTo(4);
			}
			else if(input.contains("down")){
				moveTo(5);
			}
			else {
				System.out.println("Unknown direction");
				db.addToCommands("Unknown direction");
			}
		}
		
		//__________________Picking up items___________________
		
		else if (input.contains("pick up") || input.contains("grab") || input.contains("take")) 
		{
			 
			int itemsPickedUp = 0; 
			for (Item item : itemsInRoom)
			{
				String itemName = item.getName(); 
				
				if (input.contains(itemName))
				{
					db.setItemLocation(itemName, 0);
					db.addToCommands("You picked up " + itemName);
					itemsPickedUp++; 
				}
								
			}
			
			if (itemsPickedUp == 0)
			{
				db.addToCommands("Couldn't pickup item"); 
			}
			
		//_________________Drop Item_____________________
		} 
		else if(input.contains("drop")) 
		{
			int itemsDropped = 0; 
			for (Item item : db.getItemsInLocation(0))
			{
				String itemName = item.getName(); 
				
				if (input.contains(itemName))
				{
					db.setItemLocation(itemName, db.getUserLocation());
					db.addToCommands("You dropped " + itemName + " in " + "room " + userLocation);
					itemsDropped++; 
				}
			}
			
			if (itemsDropped == 0)
			{
				db.addToCommands("You don't have that item");
			}
			
		} 
		else if(input.contains("examine"))
		{
			if(input.contains("examine ")) {
				List<Item> examinedItems = new ArrayList<Item>();
				for (Item item : usersInventory)
				{
					if(input.contains(item.getName())) {
						examinedItems.add(item);
					}
				}
				if(!examinedItems.isEmpty()) {
					for(Item item : examinedItems) {
						db.addToCommands(item.getItemDescription());
					}
				}
				else {
					db.addToCommands("You don't have the requested item(s)");
				}
			}
			else {
				db.addToCommands(db.getLocationDescriptionLong(userLocation)); 
				
				if (itemsInRoom.isEmpty())
				{
					db.addToCommands("There are no items in the room"); 
				}
				else
				{
					for (Item item : itemsInRoom)
					{
						db.addToCommands("There is a " + item.getName() + " in the room"); 
					}
				}
			}
		} 
		else if (input.contains("status"))
		{
			List<String> itemNames = new ArrayList<String>(); 
			List<String> noItems = new ArrayList<String>(); 
			
			noItems.add("No items found"); 
		
			
			if (usersInventory.size() == 0)
			{
				
				model.addInventory(noItems);
			}
			else 
			{
				for (Item item : usersInventory)
				{
					
					itemNames.add(item.getName()); 
				}
				
				model.addInventory(itemNames);
				
			}
			db.setUserScore(4);
			userScore = db.getUserScore(); 
			model.setHealth(userHealth);
			model.setScore(userScore);
			model.setPlayerLocation(userLocation);
			
			db.addToCommands("------------------------->"); 
		}
		else {
			System.out.println("Unknown command");
			db.addToCommands("Unknown command");
		}
				
		
		output();
	}
	
	public void setModel(Gameplay model)
	{
		this.model = model; 
	}

	
	
	public void output(){
		output = db.getCommands();
		model.setOutput(output);
	}
	
	
	public void moveTo(int direction) {
	
		int nextLocation = -1;
		if(direction == 0) {
			nextLocation = db.getLocationNorth(userLocation);
		}
		else if(direction == 1) {
			nextLocation = db.getLocationSouth(userLocation);
		}
		else if(direction == 2) {
			nextLocation = db.getLocationEast(userLocation);
		}
		else if(direction == 3){
			nextLocation = db.getLocationWest(userLocation);
		}
		else if(direction == 4){
			nextLocation = db.getLocationUp(userLocation);
		} 
		else if(direction == 5){
			nextLocation = db.getLocationDown(userLocation);
		}
		
		if(userLocation == nextLocation) 
		{
			System.out.println("Can't move that way");
			db.addToCommands("Can't move that way");
		}
		//__________________Puzzle_______________________
		else if(puzzle(nextLocation) == false) 
		{
			System.out.println("Room locked, need an item");
			db.addToCommands("Room locked, need an item");
		} 
		else {
			
			db.setUserLocation(nextLocation);
			userLocation = db.getUserLocation();
			String roomDescription = null;
			if(db.getPlayerHasBeen(nextLocation) == 0) {
				roomDescription = db.getLocationDescriptionLong(nextLocation);
				db.setPlayerHasBeen(nextLocation);
			}
			else if(db.getPlayerHasBeen(nextLocation) == 1) {
				roomDescription = db.getLocationDescriptionShort(nextLocation);
			}
			db.addToCommands(roomDescription);
			itemsInRoom = db.getItemsInLocation(userLocation);
			if (!itemsInRoom.isEmpty())
			{
				for (Item item : itemsInRoom)
				{
					db.addToCommands("There is a " + item.getName() + " in the room"); ///////////////////////////////////////////////////////////
				} 
			}
			
			// ____________________Agent Encounter ___________________
			for(int i = 1; i < 5; i++) {
				agentEncounter(db.getAgentLocation(i), db.getUserLocation());
			}
		}
		if(nextLocation == -1) {
			System.out.println("Movement failed, legs gone.");
		}
	}
	
	
	public void agentEncounter(int agent_location, int user_location) {
		if(agent_location == user_location) {
			db.addToCommands(db.getAgentDescription(agent_location));
		}
	}
	
	public boolean puzzle(int nextLocation) {
		// Go through player inventory
		for(Item item : usersInventory) {
			// if playerHas item and NextLocation == puzzleRoom
			String itemName = item.getName();
			if(nextLocation == 17 && itemName.equals("key")){
				return true;
			} 
		}
		
		// IF USER INVENTORY EMPTY
		// Do not let the player enter these rooms
		if(nextLocation == 17) {
			return false;
		}
		return true;
	}
	
}
