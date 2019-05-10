package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class RegisterControllerTest {
	
	private RegisterController controller;
	private IDatabase db;
	private String username = "John";
	private String password = "Doe";
	private User model = new User();
	
	@Before
	public void setUp() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model.setUsername(username);
		model.setJSPPassword(password);
		
		db.insertUserIntoUsersTable(username, password);
		
		controller = new RegisterController();
	}
	
	@Test
	public void insertUserTest() {
		//test that inserting a new user succeeded
		assertTrue(controller.insertUser(username, password));
	}
}
