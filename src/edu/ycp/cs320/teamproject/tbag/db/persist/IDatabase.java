package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.model.Location;

public interface IDatabase{
	
	public Integer findUserIDFromUsername(String username); 
	public Integer insertItem(String name, int locationID, int descriptionID);
	public Integer insertUserIntoUsersTable(String username, String password);
	public String findPasswordFromUsername(String username);
	public Boolean addUserInput(String input);
	public ArrayList<String> getInputs();
	public Integer pickupItem(String itemName, String username);
	public Integer dropItem(String itemName, String username);
	public Boolean addUserOutput(String output);
	
	public String getLocationDescriptionLong(int location_id);
	public String getLocationDescriptionShort(int location_id);
	public Integer setItemLocation(String itemName, int location);
	public Integer getItemLocationID(String itemName);
	public Integer getUserLocation(String username);
	public Integer setUserLocation(int location, String username);
	public Integer getJointLocationNorth(int currentLocation);
	public Integer getJointLocationSouth(int currentLocation);
	public Integer getJointLocationEast(int currentLocation);
	public Integer getJointLocationWest(int currentLocation);
	public Integer getPlayerHasBeen(int location, String username);
	public Integer setPlayerHasBeen(int location, String username, int flag);
	public ArrayList<String> getItemNamesInLocation(String username);
	
	public Integer deleteUserFromUsersTable(int user_id);
}

