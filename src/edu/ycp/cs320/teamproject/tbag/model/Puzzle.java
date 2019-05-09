package edu.ycp.cs320.teamproject.tbag.model;

public class Puzzle 
{ 
	private String itemName; 
	private int locationID;
	
	public void setName (String name)
	{
		this.itemName = name; 
	}
	
	public String getName()
	{
		return itemName; 
	}
	
	public void setLocationID(int locationID)
	{
		this.locationID = locationID; 
	}
	
	public int getLocationID()
	{
		return locationID; 
	}
}