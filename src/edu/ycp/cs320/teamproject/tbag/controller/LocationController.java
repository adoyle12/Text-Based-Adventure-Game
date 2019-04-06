package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Description;
import edu.ycp.cs320.teamproject.tbag.model.Location;

public class LocationController 
{
	private Location model = null; 
	
	public void setModel(Location model)
	{
		this.model = model; 
	}
	
	public void roomNumber(int roomNumber)
	{
		model.setRoomNumber(roomNumber);
	}
	
	public void description(Description description)
	{
		model.setDescription(description);
	}
}
