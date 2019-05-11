package edu.ycp.cs320.teamproject.tbag.db;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class DerbyDatabaseTest 
{
	private static IDatabase db = null;
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
		Integer user_id = db.findUserIDFromUsername("DOESNOTEXIST"); 
		
		//user_id should equal -1
		assertEquals(-1, user_id, 0.00001); 
		
		//try to find user that does exist
		user_id = db.findUserIDFromUsername(username);
		assertEquals(1, user_id, 0.0001);
		
	}
	
	
	@Test
	public void testInsertUserIntoUsersTable() 
	{
		System.out.println("Testing New User Registration");
		
		//First try user that already exists
		//Attempt to insert the new user, should come back as -1
		db.setUserFilePath(username);
		Integer user_id = db.insertUserIntoUsersTable(username, password); 
		assertEquals(-1, user_id, 0.0001); 
		
		//Okay now insert a new user
		User tester = new User();
		tester.setUsername("tester");
		tester.setJSPPassword("test");
		tester.setDBPassword("test");
		
		//Register new user
		db.setUserFilePath(username);
		user_id = db.insertUserIntoUsersTable(tester.getUsername(), tester.getJSPPassword()); 
		
		if (user_id > 0)
		{
			db.setUserFilePath(tester.getUsername());
			Integer matchingID = db.findUserIDFromUsername(tester.getUsername()); 
			
			//ID's should match
			assertEquals(user_id, matchingID, 0.0001); 
			
			//delete the user so you don't impact database
			db.deleteUserFromUsersTable(user_id);
		}
	}
	
	@Test
	public void testGetSetUserLocation() {
		//test getting and setting user location
		db.setUserFilePath(username);
		db.setUserLocation(7);//set's user's location to room 7
		
		int location = db.getUserLocation();
		assertEquals(7, location);//test that user's location is now room 7
		
		db.setUserLocation(10);
		location = db.getUserLocation();
		assertEquals(10, location);//test that user's location was changed to room 10
	}
	
	@AfterClass
	public static void cleanUp() {
		
		//remove test user from database
		int user_id = db.findUserIDFromUsername("foxy");
		db.deleteUserFromUsersTable(user_id);
		
	}

}
