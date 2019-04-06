package edu.ycp.cs320.teamproject.tbag.model;

public class Item 
{
	
	private String name; 
	private Location location;
	
	public void setName (String name)
	{
		this.name = name; 
	}
	
	public String getName()
	{
		return name; 
	}
	
	public void setLocation(Location location)
	{
		this.location = location; 
	}
	
	public Location getLocation()
	{
		return location; 
	}
}
