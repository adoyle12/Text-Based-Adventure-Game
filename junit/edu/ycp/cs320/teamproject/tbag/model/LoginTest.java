package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoginTest 
{
	private Login model;
	
	@Before
	public void setUp() 
	{
		model = new Login();
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
		model.setPassword("password");
		assertEquals("password", model.getPassword());
	}
	
	@Test
	public void testSetCredentials()
	{
		//credentials are currently hard coded to only pass for username == "hello" and password == "world" 
		 model.setUsername("hello");
		 model.setPassword("world");
		 model.setCredentials();
		 assertTrue(model.getCredentials());
		 
		 model.setUsername("John");
		 model.setPassword("Watson");
		 model.setCredentials();
		 assertFalse(model.getCredentials()); 
	}

}
