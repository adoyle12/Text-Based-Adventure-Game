package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{
	private User model;
	
	@Before
	public void setUp() 
	{
		model = new User();
		model.setUsername("John");
		model.setDBPassword("Doe");
		model.setJSPPassword("Doe");
		model.setCredentials();
		model.setLocationID(1);
	}
	
	@Test
	public void testSetUsername() 
	{
		//test that user name was successfully saved
		assertEquals("John", model.getUsername()); 
	}
	
	@Test
	public void testSetPassword()
	{
		//test that DBPassword and JSPPassword were set correctly
		assertEquals("Doe", model.getDBPassword());
		assertEquals("Doe", model.getJSPPassword());
		//test that DBPassword and JSPPassword match
		assertEquals(model.getDBPassword(), model.getJSPPassword());
		
		model.setDBPassword("right");
		model.setJSPPassword("wrong");
		//test that it acknowledges when DBPassword and JSPPassword do not match
		assertTrue(model.getDBPassword() != model.getJSPPassword());
	}
	
	@Test
	public void testSetCredentials()
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
	public void testSetLocationID() {
		//tests that user's location is set to room 1
		assertEquals(1, model.getLocationID());
		
		//tests that user's location doesn't register as any other room
		for(int i = 2; i < 9; i++) {
			assertTrue(model.getLocationID() != i);
		}
		
		model.setLocationID(2);
		//tests that user's location is changed to room 2
		assertEquals(2, model.getLocationID());
		assertTrue(model.getLocationID() != 1);
	}
}
