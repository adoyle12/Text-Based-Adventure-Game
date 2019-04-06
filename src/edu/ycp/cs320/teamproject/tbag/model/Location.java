package edu.ycp.cs320.teamproject.tbag.model;


public class Location 
{
	
	private int roomNumber; 
	private Description description; 
	
	public void setRoomNumber (int roomNumber)
	{
		this.roomNumber = roomNumber; 
	}
	
	public int getRoomNumber()
	{
		return roomNumber; 
	}
	
	public void setDescription(Description description)
	{
		this.description = description; 
	}
	
	public Description getDescription()
	{
		return description; 
	}
	
}
