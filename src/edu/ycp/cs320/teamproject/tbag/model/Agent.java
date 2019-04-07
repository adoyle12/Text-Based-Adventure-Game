package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;

public class Agent 
{
	private int agentID; 
	private int locationID; 
	private ArrayList<Item> inventory; 
	
	public void setAgentID(int agentID)
	{
		this.agentID = agentID; 
	}
	
	public int getAgentID()
	{
		return agentID; 
	}
	
	public void setLocationID(int locationID)
	{
		this.locationID = locationID; 
	}
	
	public int getLocationID()
	{
		return locationID; 
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
