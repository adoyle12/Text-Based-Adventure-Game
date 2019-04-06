package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Agent;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;

public class AgentController 
{
	private Agent model;
	
	public void setModel (Agent model)
	{
		this.model = model; 
	}
	
	public void location(Location location)
	{
		model.setLocation(location);
	}
	
	public void item(Item item)
	{
		model.addItem(item);
	}
}
