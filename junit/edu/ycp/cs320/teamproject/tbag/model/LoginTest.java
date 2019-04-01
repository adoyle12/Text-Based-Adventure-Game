package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
	
	private Login login;
	private String username;
	private String password;
	
	@Before
	public void setUp() {
		// These are the hard coded username and password
		username = "hello";
		password = "world";
		login = new Login();
	}
	
	@Test
	public void testGetAndSetUsername() throws Exception {
		//test getting username
		login.setUsername(username);
		assertEquals(username, login.getUsername());
	}
	
	@Test
	public void testGetAndSetPassword() throws Exception {
		//test getting password
		login.setPassword(password);
		assertEquals(password, login.getPassword());
	}
	
	@Test
	public void testSetCheckUsername() throws Exception {
		login.setUsername(username);
		assertEquals(true, login.setCheckUsername(username));
	}
	
	@Test
	public void testSetCheckPassword() throws Exception {
		login.setPassword(password);
		assertEquals(true, login.setCheckPassword(password));
	}
}
