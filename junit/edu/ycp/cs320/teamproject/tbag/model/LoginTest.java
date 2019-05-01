package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LoginTest
{
	private User model;
	
	@Before
	public void setUp() 
	{
		model = new User();
	}
	
	@Test
	public void testSetUsername() 
	{
		model.setUsername("Test");
		assertEquals("Test", model.getUsername()); 
	}
	
	@Test
	public void testSetPassword()
	{
		model.setDBPassword("password");
		model.setJSPPassword("password");
		assertEquals("password", model.getDBPassword());
		assertEquals("password", model.getJSPPassword());
	}
	
	@Test
	public void testSetCredentials()
	{
		//credentials are currently hard coded to only pass for username == "hello" and password == "world" 
		 model.setUsername("hello");
		 model.setDBPassword("world");
		 model.setJSPPassword("world");
		 model.setCredentials();
		 assertTrue(model.getCredentials());
		 
		 model.setUsername("John");
		 model.setDBPassword("Watson");
		 model.setJSPPassword("Wrong");
		 model.setCredentials();
		 assertFalse(model.getCredentials()); 
	}
}
