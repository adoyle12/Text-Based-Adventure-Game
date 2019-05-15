package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.util.ArrayList;
import java.util.List;
import edu.ycp.cs320.teamproject.tbag.model.Item;
/*
 * Some or all code borrowed from CS320 Library Example
 */

public interface IDatabase{
	
	public Integer findUserIDFromUsername(String username);
	public Integer insertUserIntoUsersTable(String username, String password);
	public String findPasswordFromUsername(String username);
	public Boolean addToCommands(String input);
	public ArrayList<String> getCommands();
	
	public String getLocationDescriptionLong(int location_id);
	public String getLocationDescriptionShort(int location_id);
	public void setItemLocation(String itemName, int location);
	public Integer getItemLocationID(String itemName);
	public List<Item> getItemsInLocation(final int locationID);
	public ArrayList<String> getItemDescription(int item_location);
	public Integer getUserLocation();
	public void setUserLocation(int location);
	public Integer getLocationNorth(int currentLocation);
	public Integer getLocationSouth(int currentLocation);
	public Integer getLocationEast(int currentLocation);
	public Integer getLocationWest(int currentLocation);
	public Integer getLocationUp(int currentLocation);
	public Integer getLocationDown(int currentLocation);
	public Integer getPlayerHasBeen(int location);
	public void setPlayerHasBeen(int location);
	public Integer getAgentLocation(int agent_id);
	public String getAgentDescription(int agent_id);
	public void setAgentLocation(int agent_id, int location); 
	
	public Integer deleteUserFromUsersTable(int user_id);
	public void setUserFilePath(String username);
	public void resetGame();
	public Integer getUserScore();
	public Integer getUserHealth();
	public void setUserHealth(int healthPoints);
	public void setUserScore(int scorePoints);
}