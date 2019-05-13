package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.*;

public class GameplayControllerTest 
{
	private Gameplay model;
	private GameplayController controller; 
	private User user;
	private String username;
	private String password;
	private Item item;
	private static IDatabase db;
	
	@Before
	public void setUp() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		username = "john";
		password = "doe";
		user = new User();
		user.setUsername(username);
		user.setJSPPassword(password);
		user.setDBPassword(password);
		
		db.insertUserIntoUsersTable(username, password);
		
	}
	
	@Test
	public void testInput()
	{
		model = new Gameplay();
		
		db.setUserFilePath(username);
		
		controller = new GameplayController(username, false);
		
		controller.setModel(model);
		
		controller.gameLogic("Test");
		assertEquals("Test", model.getInput()); 
	}
	
	@Test
	public void testGoNorth() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(5);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go north");
		int userLocation = db.getUserLocation();
		assertEquals(2, userLocation);
		
	}
	
	@Test
	public void testGoSouth() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(7);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go south");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	public void testGoEast() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(3);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go east");
		int userLocation = db.getUserLocation();
		assertEquals(4, userLocation);
		
	}
	
	@Test
	public void testGoWest() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(3);

		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go west");
		int userLocation = db.getUserLocation();
		assertEquals(2, userLocation);
		
	}
	
	@Test
	public void testGoUp() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(1);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go up");
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);
		
	}
	
	@Test
	public void testGoDown() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(6);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go down");
		int userLocation = db.getUserLocation();
		assertEquals(1, userLocation);
		
	}
	
	@Test
	public void testMoveNorth() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(5);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move north");
		int userLocation = db.getUserLocation();
		assertEquals(2, userLocation);
		
	}
	
	@Test
	public void testMoveSouth() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(7);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move south");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	public void testMoveEast() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(9);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move east");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	@Test
	public void testMoveWest() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(3);

		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move west");
		int userLocation = db.getUserLocation();
		assertEquals(2, userLocation);
		
	}
	
	@Test
	public void testMoveUp() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(1);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move up");
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);
		
	}
	
	@Test
	public void testMoveDown() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(6);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move down");
		int userLocation = db.getUserLocation();
		assertEquals(1, userLocation);
		
	}
	
	@Test
	public void testPickUpItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance(); 
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(6);//set user's location to the room with the sword
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);//check that the user is in room 6 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 6
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 6
		item = itemsInRoom.get(0); //there should only be a sword in room 6
		assertEquals("sword", item.getName());//check that the item in the room is a sword
		int itemLocation = db.getItemLocationID("sword");
		assertEquals(6, itemLocation);//checks that the sword's location is room 6, where it should be
		
		controller.gameLogic("pick up sword");
		itemLocation = db.getItemLocationID("sword");
		assertEquals(0, itemLocation);//check that sword's location was updated to the user's inventory
		
		db.setItemLocation("sword", 6);//place sword back in room 6 to be able to run test again
		
	}
	
	@Test
	public void testGrabItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance(); 
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(7);//set user's location to the room with the stick
		int userLocation = db.getUserLocation();
		assertEquals(7, userLocation);//check that the user is in room 7 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 7
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 7
		item = itemsInRoom.get(0); //there should only be a stick in room 7
		assertEquals("stick", item.getName());//check that the item in the room is a stick
		int itemLocation = db.getItemLocationID("stick");
		assertEquals(7, itemLocation);//checks that the stick's location is room 7, where it should be
		
		controller.gameLogic("grab stick");
		itemLocation = db.getItemLocationID("stick");
		assertEquals(0, itemLocation);//check that stick's location was updated to the user's inventory
		
		db.setItemLocation("stick", 7);//place stick back in room 7 for testing purposes
		
	}
	
	@Test
	public void testTakeItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance(); 
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(8);//set user's location to the room with the dagger
		int userLocation = db.getUserLocation();
		assertEquals(8, userLocation);//check that the user is in room 8 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 8
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 8
		item = itemsInRoom.get(0); //there should only be a dagger in room 8
		assertEquals("dagger", item.getName());//check that the item in the room is a dagger
		int itemLocation = db.getItemLocationID("dagger");
		assertEquals(8, itemLocation);//checks that the dagger's location is room 8, where it should be
		
		controller.gameLogic("take dagger");
		itemLocation = db.getItemLocationID("dagger");
		assertEquals(0, itemLocation);//check that dagger's location was updated to the user's inventory
		
		db.setItemLocation("dagger", 8);//place dagger back in room 8 for testing purposes
		
	}
	
	@Test
	public void testDropItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance(); 
		
		model = new Gameplay();
		
		db.setUserFilePath(username);
		db.setUserLocation(10);//set user's location to room 10
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);//check that the user is in room 10 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 10
		assertEquals(0, itemsInRoom.size());//check that there are no items in room 10
		int itemLocation = db.getItemLocationID("goddess note");
		assertEquals(0, itemLocation);//checks that the goddess note is in the user's inventory
		
		controller.gameLogic("drop goddess note");
		itemLocation = db.getItemLocationID("goddess note");
		assertEquals(10, itemLocation);//check that goddess note is now in room 10
		
		db.setItemLocation("goddess note", 0);//place note back in user's inventory for testing purposes
		
	}
	
	@AfterClass
	public static void cleanUp() {
		
		//remove test user from database
		int user_id = db.findUserIDFromUsername("john");
		db.deleteUserFromUsersTable(user_id);
		
	}
	
}
