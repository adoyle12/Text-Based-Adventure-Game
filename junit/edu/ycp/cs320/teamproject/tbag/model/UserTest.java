package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;

public class UserTest
{
	private User model;
	private IDatabase db;
	
	@Before
	public void setUp() 
	{
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new User();//create user
		model.setUsername("John");//set username
		
		//set user password
		model.setDBPassword("Doe");
		model.setJSPPassword("Doe");
		
		model.setCredentials();//set credentials
		model.setLocationID(1);//set user's initial location
	}
	
	@Test
	public void testGetSetUsername() 
	{
		//test that user name was successfully saved
		assertEquals("John", model.getUsername()); 
		
		model.setUsername("Bob");
		assertEquals("Bob", model.getUsername());
		
	}
	
	@Test
	public void testGetSetPassword()
	{
		//insert user into database for testing
		db.insertUserIntoUsersTable(model.getUsername(), model.getJSPPassword());
		
		//test that DBPassword and JSPPassword were set correctly
		assertEquals("Doe", model.getDBPassword());
		assertEquals("Doe", model.getJSPPassword());
		//test that DBPassword and JSPPassword match
		assertEquals(model.getDBPassword(), model.getJSPPassword());
	}
	
	@Test
	public void testGetSetCredentials()
	{
		//asserts that credentials were saved correctly
		 assertTrue(model.getCredentials());
		 
		 //tests that credentials are incorrect if something is wrong
		 model.setUsername("John");
		 model.setDBPassword("Watson");
		 model.setJSPPassword("Wrong");
		 
		 model.setCredentials();
		 assertFalse(model.getCredentials()); 
	}
	
	@Test
	public void testGetSetLocationID() {
		//tests that user's location is set to room 1
		assertEquals(1, model.getLocationID());
		
		model.setLocationID(2);
		//tests that user's location is changed to room 2
		assertEquals(2, model.getLocationID());
	}
}
