package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.teamproject.tbag.model.*;

public class LoginControllerTest 
{
	private Login model;
	private LoginController controller; 
	
	@Before
	public void setUp() 
	{
		model = new Login();
		controller = new LoginController();
		
		controller.setModel(model);
	}
	
	@Test
	public void testCheckCredentials()
	{
		//These are hard coded in right now to username == "hello" & password == "world"
		controller.credentials("hello", "world");
		assertTrue(model.getCredentials()); 
		
		controller.credentials("sherlock", "holmes");
		assertFalse(model.getCredentials()); 
	}
}
