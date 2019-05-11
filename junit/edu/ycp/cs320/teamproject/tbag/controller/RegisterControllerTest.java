package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class RegisterControllerTest {
	
	private RegisterController controller;
	private static IDatabase db;
	private String username = "Jane";
	private String password = "Doe";
	private User model = new User();
	private Boolean result;
	
	@Before
	public void setUp() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model.setUsername(username);
		model.setJSPPassword(password);
		
		controller = new RegisterController();
		controller.insertUser(username, password);
	}
	
	@Test
	public void insertNewUserTest() {
		
		//test that inserting a new user succeeded
		String user = "itty";
		String pass = "bitty";
		result = controller.insertUser(user, pass);
		assertEquals(true, result);
		assertEquals(pass, db.findPasswordFromUsername(user));
	
	}
	
	@Test
	public void testInsertUserExists() {
		
		//tests that it knows the user already exists
		result = controller.insertUser(username, password);
		assertEquals(false, result);
		
	}
	
	@AfterClass
	public static void cleanUp() {
		
		//remove test user from database
		int user1 = db.findUserIDFromUsername("Jane");
		db.deleteUserFromUsersTable(user1);
		
		int user2 = db.findUserIDFromUsername("itty");
		db.deleteUserFromUsersTable(user2);
		
	}
	
}
