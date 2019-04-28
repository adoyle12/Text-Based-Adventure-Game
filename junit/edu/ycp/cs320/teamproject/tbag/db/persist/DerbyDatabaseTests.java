package edu.ycp.cs320.teamproject.tbag.db.persist;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.model.User;

public class DerbyDatabaseTests 
{
	private IDatabase db = null; 
	private ArrayList<User> users = null; 
	
	
	@Before
	public void setUp() throws Exception {
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
		
		
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
		String username = "alex"; 
		String password = "doyle"; 
		
		//Attempt to insert the new user, should come back as -1
		
		Integer user_id = db.insertUserIntoUsersTable(username, password); 
		
		assertEquals(-1, user_id, 0.0001); 
		
		
		
		//Okay now insert a new user 
		
		username = "tester"; 
		password = "password"; 
		
		//Register new user
		
		user_id = db.insertUserIntoUsersTable(username, password); 
		
		if (user_id > 0)
		{
			Integer matchingID = db.findUserIDFromUsername(username); 
			
			//ID's should match
			assertEquals(user_id, matchingID, 0.0001); 
		}
		
		
		
	}

}
