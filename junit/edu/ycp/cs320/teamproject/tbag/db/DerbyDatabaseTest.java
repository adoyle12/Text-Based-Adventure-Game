package edu.ycp.cs320.teamproject.tbag.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class DerbyDatabaseTest 
{
	private IDatabase db = null; 
	private ArrayList<User> users = null;
	private User user = null;
	private String username;
	private String password;
	
	
	@Before
	public void setUp() throws Exception {
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		username = "foxy";
		password = "lady";
		user = new User();
		user.setUsername(username);
		user.setJSPPassword(password);
		user.setDBPassword(password);
		
		db.insertUserIntoUsersTable(username, password);
		
	}
	
	@Test
	public void testFindUserIDFromUsername()
	{
		System.out.println("Testing Finding UserID from Username");
		
		//try to find user that doesn't exist
		String username = "DOESNOTEXIST"; 
		
		Integer user_id = db.findUserIDFromUsername(username); 
		
		//user_id should equal 0
		assertEquals(0, user_id, 0.00001); 
		
		//try to find user that does exist
		username = "alex"; 
		
		user_id = db.findUserIDFromUsername(username);
		
		//AD's user_id should = 3
		assertEquals(3, user_id, 0.0001);
		
	}
	
	
	@Test
	public void testInsertUserIntoUsersTable() 
	{
		System.out.println("Testing New User Registration");
		
		//First try user that already exists
		username = "alex"; 
		password = "doyle";
		
		//Attempt to insert the new user, should come back as -1
		db.setUserFilePath(username);
		Integer user_id = db.insertUserIntoUsersTable(username, password); 
		assertEquals(-1, user_id, 0.0001); 
		
		
		//Okay now insert a new user 
		username = "tester"; 
		password = "password"; 
		
		//Register new user
		db.setUserFilePath(username);
		user_id = db.insertUserIntoUsersTable(username, password); 
		
		if (user_id > 0)
		{
			db.setUserFilePath(username);
			Integer matchingID = db.findUserIDFromUsername(username); 
			
			//ID's should match
			assertEquals(user_id, matchingID, 0.0001); 
			
			//delete the user so you don't impact database
			db.deleteUserFromUsersTable(user_id);
		}
	}
	
	@Test
	public void testUserLocation() {
		//test getting and setting user location
		db.setUserFilePath(username);
		db.setUserLocation(7);
		
		int location = db.getUserLocation();
		
		assertEquals(7, location);
	}

}
