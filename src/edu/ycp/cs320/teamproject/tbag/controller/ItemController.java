package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;

public class ItemController 
{
	private Item model = null; 
	
	public void setModel (Item model)
	{
		this.model = model; 
	}
	
	public void itemID (int itemID)
	{
		model.setItemID(itemID);
	}
	public void name(String name)
	{
		model.setName(name);
	}
	
	public void location(int locationID)
	{
		model.setLocationID(locationID);
	}
}

