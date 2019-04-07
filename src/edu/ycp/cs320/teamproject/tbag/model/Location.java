package edu.ycp.cs320.teamproject.tbag.model;

public class Location 
{
	
	private int locationID; 
	private String shortDescription; 
	private String longDescription; 
	
	public void setLocationID (int locationID)
	{
		this.locationID = locationID; 
	}
	
	public int getLocationID()
	{
		return locationID; 
	}
	
	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription; 
	}
	
	public String getShortDescription()
	{
		return shortDescription; 
	}
	
	public void setLongDescription(String longDescription)
	{
		this.longDescription = longDescription; 
	}
	
	public String getLongDescription()
	{
		return longDescription; 
	}
}

