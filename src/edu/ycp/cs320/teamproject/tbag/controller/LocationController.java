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
	
	public void locationID(int locationID)
	{
		model.setLocationID(locationID);
	}
	
	public void shortDescription(String shortDescription)
	{
		model.setShortDescription(shortDescription);
	}
	
	public void longDescription(String longDescription)
	{
		model.setLongDescription(longDescription);
	}
}
