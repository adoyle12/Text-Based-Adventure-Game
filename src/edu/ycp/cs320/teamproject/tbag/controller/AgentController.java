package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Agent;
import edu.ycp.cs320.teamproject.tbag.model.Item;

public class AgentController 
{
	private Agent model;
	
	public void setModel (Agent model)
	{
		this.model = model; 
	}
	
	public void agentID(int agentID)
	{
		model.setAgentID(agentID);
	}
	
	public void location(int locationID)
	{
		model.setLocationID(locationID);
	}
	
	public void item(Item item)
	{
		model.addItem(item);
	}
}
