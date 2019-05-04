package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.model.Description;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.JointLocations;
import edu.ycp.cs320.teamproject.tbag.model.Location;
import edu.ycp.cs320.teamproject.tbag.model.Puzzle;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class InitialData 
{
	
	public static List<Item> getInventory() throws IOException{
		List<Item> inventory = new ArrayList<Item>();
		ReadCSV readInventory = new ReadCSV("inventory.csv");
		try 
		{
			//auto-generated primary key for inventory table
			Integer itemID = 1; 
			while (true) 
			{
				List<String> tuple = readInventory.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				
				item.setItemID(itemID++);
				item.setLocationID(Integer.parseInt(i.next()));
				item.setName(i.next());
				inventory.add(item);
			}
			System.out.println("Inventory loaded from CSV file");
			return inventory;
		} 
		finally 
		{
			readInventory.close();
		}
	}
	
	public static List<Location> getLocations() throws IOException{
		List<Location> locationList = new ArrayList<Location>();
		ReadCSV readLocations = new ReadCSV("locations.csv");
		try 
		{
			//auto-generated primary key for location table
			Integer locationID = 1; 
			while (true) 
			{
				List<String> tuple = readLocations.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				Location location = new Location();
				location.setLocationID(locationID++);
				location.setShortDescription(i.next());
				location.setLongDescription(i.next());
				location.setPlayerHasBeen(Integer.parseInt(i.next()));
				locationList.add(location); 
				
				
			}
			System.out.println("Locations loaded from CSV file");
			return locationList;
		} 
		finally 
		{
			readLocations.close();
		}
		
		
	}
	
	public static List<JointLocations> getJointLocations() throws IOException{
		List<JointLocations> jointLocationsList = new ArrayList<JointLocations>();
		ReadCSV readLocations = new ReadCSV("jointLocations.csv");
		try 
		{ 
			while (true)
			{
				List<String> tuple = readLocations.next();
				if (tuple == null)
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				JointLocations jointLocations = new JointLocations();
				jointLocations.setLocationID(Integer.parseInt(i.next()));
				jointLocations.setLocationNorth(Integer.parseInt(i.next()));
				jointLocations.setLocationSouth(Integer.parseInt(i.next()));
				jointLocations.setLocationEast(Integer.parseInt(i.next()));
				jointLocations.setLocationWest(Integer.parseInt(i.next()));
				
				jointLocationsList.add(jointLocations);
				
			}
			System.out.println("JointLocations loaded from CSV file");
			return jointLocationsList;
		} 
		finally 
		{
			readLocations.close();
		}
		
	}
	
	public static List<User> getUsers() throws IOException{
		List<User>  userList = new ArrayList<User>();
		ReadCSV readUsers = new ReadCSV("users.csv");
		try 
		{
			//auto-generated primary key for location table
			Integer agentID = 1;
			Integer userID = 1;
			while (true) 
			{
				List<String> tuple = readUsers.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				User user = new User(); 
				user.setAgentID(agentID++);
				user.setUsername(i.next());
				user.setDBPassword(i.next());
				user.setJSPPassword(i.next());
				user.setLocationID(userID);
				userList.add(user);
				
				
			}
			System.out.println("Users loaded from CSV file");
			return userList;
		} 
		finally 
		{
			readUsers.close();
		}
		
		
	}

	public static List<Description> getDescriptions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static List<Puzzle> getPuzzle() throws IOException{
		List<Puzzle> puzzleList = new ArrayList<Puzzle>();
		ReadCSV readPuzzle = new ReadCSV("puzzle.csv");
		try 
		{
			while (true) 
			{
				List<String> tuple = readPuzzle.next();
				if (tuple == null) 
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				Puzzle puzzle = new Puzzle();
				
				puzzle.setLocationID(Integer.parseInt(i.next()));
				puzzle.setName(i.next());
				puzzleList.add(puzzle);
			}
			System.out.println("Puzzle loaded from CSV file");
			return puzzleList;
		} 
		finally 
		{
			readPuzzle.close();
		}
	}	
}