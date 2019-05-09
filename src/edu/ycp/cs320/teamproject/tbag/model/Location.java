package edu.ycp.cs320.teamproject.tbag.model;

public class Location 
{
	
	private int locationID; 
	private String shortDescription; 
	private String longDescription; 
	private int playerHasBeen;
	private int locationNorth;
	private int locationSouth;
	private int locationEast;
	private int locationWest;
	private int locationUp;
	private int locationDown;
	
	public void setLocationID (int locationID)
	{
		this.locationID = locationID; 
	}
	
	public int getLocationID()
	{
		return locationID; 
	}
	
	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription; 
	}
	
	public String getShortDescription()
	{
		return shortDescription; 
	}
	
	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription; 
	}
	
	public String getLongDescription()
	{
		return longDescription; 
	}
	
	// Integer (either 1 or 0) to see if the player has been to this location yet
	public void setPlayerHasBeen (Integer flag)
	{
		this.playerHasBeen = flag; 
	}
	
	public int getPlayerHasBeen()
	{
		return playerHasBeen; 
	}
	
	public void setLocationNorth(int locationNorth)
	{
		this.locationNorth = locationNorth; 
	}
	
	public int getLocationNorth()
	{
		return locationNorth; 
	}
	
	public void setLocationSouth(int locationSouth)
	{
		this.locationSouth = locationSouth; 
	}
	
	public int getLocationSouth()
	{
		return locationSouth; 
	}
	
	public void setLocationEast(int locationEast)
	{
		this.locationEast = locationEast; 
	}
	
	public int getLocationEast()
	{
		return locationEast; 
	}
	
	public void setLocationWest(int locationWest)
	{
		this.locationWest = locationWest; 
	}
	
	public int getLocationWest()
	{
		return locationWest; 
	}
	
	public int getLocationUp() {
		return locationUp;
	}

	public void setLocationUp(int locationUp) {
		this.locationUp = locationUp;
	}

	public int getLocationDown() {
		return locationDown;
	}

	public void setLocationDown(int locationDown) {
		this.locationDown = locationDown;
	}
}

