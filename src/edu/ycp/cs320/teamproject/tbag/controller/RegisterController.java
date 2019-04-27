package edu.ycp.cs320.teamproject.tbag.controller;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;

public class RegisterController 
{

	
	private IDatabase db = null; 
	
	public RegisterController()
	{
		//creating DB instance here - thanks CS320 Library Reference
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance(); 
	}
	
	public boolean insertUser(String username, String password)
	{
		Integer user_id = db.insertUserIntoUsersTable(username, password); 
		
		//check if the insertion succeeded
		if (user_id > 0)
		{
			System.out.println("New user: (ID: " + user_id + ") successfully added to Users table");
			return true; 
		}
		else
		{
			System.out.println("Failed to insert new user");
			return false; 
		}
	}
}
