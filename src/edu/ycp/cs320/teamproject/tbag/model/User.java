package edu.ycp.cs320.teamproject.tbag.model;

public class User extends Agent
{	
	private String username; 
	private String password;
	//private Integer location_id;
	private boolean credentials; 
	
	
	public void setUsername (String username) 
	{
		this.username = username;
	}	
	
	public String getUsername() 
	{
		return username;
	}
	

	public void setPassword (String password) 
	{
		this.password = password;
	}	
	
	public String getPassword() 
	{
		return this.password;
	}
	
	/*public void setLocationID(int location_id) {
		this.location_id = location_id;
	}
	
	public int getLocationID() {
		return location_id;
	}*/
	
}
