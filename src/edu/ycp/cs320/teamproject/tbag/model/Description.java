package edu.ycp.cs320.teamproject.tbag.model;

public class Description 
{
	private String shortDescription; 	//Short descriptions
	private String longDescription; 	//Long	descriptions
	
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
