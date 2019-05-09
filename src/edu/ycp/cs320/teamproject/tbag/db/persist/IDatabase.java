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
	public Integer setItemLocation(String itemName, int location, int onUserFlag);
	public Integer getItemLocationID(String itemName);
	public String getItemDescription(int item_location, int onUserFlag);
	public Integer getUserLocation();
	public Integer setUserLocation(int location);
	public Integer getLocationNorth(int currentLocation);
	public Integer getLocationSouth(int currentLocation);
	public Integer getLocationEast(int currentLocation);
	public Integer getLocationWest(int currentLocation);
	public Integer getLocationUp(int currentLocation);
	public Integer getLocationDown(int currentLocation);
	public Integer getPlayerHasBeen(int location);
	public Integer setPlayerHasBeen(int location, int flag);
	public Integer getAgentLocation(int agent_id);
	public String getAgentDescription(int agent_id);
	
	public Integer deleteUserFromUsersTable(int user_id);
	public void setUserFilePath(String username);
}

