package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.model.Location;

public interface IDatabase{
	
	public Integer findUserIDFromUsername(String username); 
	public Integer insertItem(String name, int locationID, int descriptionID);
	public Location getLocationDescriptionLong(int location_id);
	public Integer getItemLocationID(String itemName);
	public Integer insertUserIntoUsersTable(String username, String password);
	public String findPasswordFromUsername(String username);
	public Boolean addUserInput(String input);
	public ArrayList<String> getInputs();
	public Integer getUserLocation(String username);
	public Integer setUserLocation(int location, String username);
	public Integer pickupItem(String itemName, String username);
	public Integer dropItem(String itemName, String username);
	public Integer setItemLocation(String itemName, String username);
}

