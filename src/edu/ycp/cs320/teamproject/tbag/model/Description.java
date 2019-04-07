package edu.ycp.cs320.teamproject.tbag.model;

public class Description 
{
	private int descriptionID; 
	private String shortDescription; 	//Short descriptions
	private String longDescription; 	//Long	descriptions
	
	public void setDescriptionID(int descriptionID)
	{
		this.descriptionID = descriptionID; 
	}
	
	public int getDescriptionID()
	{
		return descriptionID; 
	}
	
	public void setShort(String shortDescription)
	{
		this.shortDescription = shortDescription;
	}
	
	public String getShort()
	{
		return shortDescription;
	}
	
	public void setLong(String longDescription)
	{
		this.longDescription = longDescription; 
	}
	
	public String getLong()
	{
		return longDescription; 
	}
}
