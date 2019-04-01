package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.controller.GuessingGameController;
import edu.ycp.cs320.teamproject.tbag.model.GuessingGame;
import edu.ycp.cs320.teamproject.tbag.model.Login;

public class LoginControllerTest 
{
	private Login loginModel;
	private LoginController controller;
	private String username;
	private String password;
	
	@Before
	public void setUp() 
	{
		loginModel = new Login();
		controller = new LoginController();
		
		// These are the hard coded username and password
		username = "hello";
		password = "world";
		
		controller.setModel(loginModel);
	}
	
	@Test
	public void testSetModel() 
	{
		loginModel.setPassword(password);
		loginModel.setUsername(username);
		controller.setModel(loginModel);
		assertEquals(password, loginModel.getPassword());
		assertEquals(username, loginModel.getUsername());
	}
	
	@Test
	public void testCheckID() {
		// Test when both are 'correct'
		assertTrue(controller.checkID(username, password));
		
		// Test when one or more are 'wrong'
		assertEquals(false, controller.checkID(username, "Planet"));
		assertEquals(false, controller.checkID("Goodbye", "Planet"));
	}
}
