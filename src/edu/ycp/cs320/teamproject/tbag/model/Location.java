package edu.ycp.cs320.teamproject.tbag.model;

public class Location 
{
	
	private int locationID; 
	private String shortDescription; 
	private String longDescription; 
	private int playerHasBeen;
	
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
	
}

