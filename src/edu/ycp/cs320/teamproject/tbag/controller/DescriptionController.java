package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.model.Description;

public class DescriptionController 
{
	private Description model = null; 
	
	public void setModel (Description model)
	{
		this.model = model; 
	}
	
	
	public void descriptionID(int descriptionID)
	{
		model.setDescriptionID(descriptionID);
	}
	
	public void shortDescription(String shortDescription)
	{
		model.setShort(shortDescription);
	}
	
	public void longDescription(String longDescription)
	{
		model.setLong(longDescription);
	}
}
