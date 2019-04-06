package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;

public class Agent 
{
	private Location location; 
	private ArrayList<Item> inventory; 
	
	public void setLocation(Location location)
	{
		this.location = location; 
	}
	
	public Location getLocation()
	{
		return location; 
	}
	
	public void addItem(Item item)
	{
		inventory.add(item); 
	}
	
	public ArrayList<Item> getInventory()
	{
		return inventory; 
	}
	
}
