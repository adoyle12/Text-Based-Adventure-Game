package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		
		model = new Gameplay();
		
	}
	
	@Test
	public void testInput()
	{
		
		db.setUserFilePath(username);
		
		controller = new GameplayController(username, false);
		
		controller.setModel(model);
		
		controller.gameLogic("Test");
		assertEquals("Test", model.getInput()); 
	}
	
	@Test
	public void testGoNorth() {

		db.setUserFilePath(username);
		db.setUserLocation(6);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go north");
		int userLocation = db.getUserLocation();
		assertEquals(3, userLocation);
		
	}
	
	@Test
	public void testGoSouth() {
	
		db.setUserFilePath(username);
		db.setUserLocation(7);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go south");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	public void testGoEast() {

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

		db.setUserFilePath(username);
		db.setUserLocation(1);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("go up");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	@Test
	public void testMoveNorth() {

		db.setUserFilePath(username);
		db.setUserLocation(9);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move north");
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);
		
	}
	
	@Test
	public void testMoveSouth() {
	
		db.setUserFilePath(username);
		db.setUserLocation(3);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move south");
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);
		
	}
	
	public void testMoveEast() {

		db.setUserFilePath(username);
		db.setUserLocation(6);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move east");
		int userLocation = db.getUserLocation();
		assertEquals(7, userLocation);
		
	}
	
	@Test
	public void testMoveWest() {

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
	
		db.setUserFilePath(username);
		db.setUserLocation(13);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move up");
		int userLocation = db.getUserLocation();
		assertEquals(22, userLocation);
		
	}
	
	@Test
	public void testPickUpItem() {
	
		db.setUserFilePath(username);
		db.setUserLocation(11);//set user's location to the room with the green potion
		int userLocation = db.getUserLocation();
		assertEquals(11, userLocation);//check that the user is in room 11
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 11
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 11
		item = itemsInRoom.get(0); //there should only be a green potion in room 11
		assertEquals("green potion", item.getName());//check that the item in the room is a key
		int itemLocation = db.getItemLocationID("green potion");
		assertEquals(11, itemLocation);//checks that the green potion's location is room 11, where it should be
		
		controller.gameLogic("pick up green potion");
		itemLocation = db.getItemLocationID("green potion");
		assertEquals(0, itemLocation);//check that green potion's location was updated to the user's inventory
		
		db.setItemLocation("green potion", 11);//return the green potion to original room
		
	}
	
	@Test
	public void testGrabItem() {
	
		db.setUserFilePath(username);
		db.setUserLocation(19);//set user's location to the room with the sword
		int userLocation = db.getUserLocation();
		assertEquals(19, userLocation);//check that the user is in room 19 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 19
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 7
		item = itemsInRoom.get(0); //there should only be a sword in room 19
		assertEquals("sword", item.getName());//check that the item in the room is a sword
		int itemLocation = db.getItemLocationID("sword");
		assertEquals(19, itemLocation);//checks that the sword's location is where it should be
		
		controller.gameLogic("grab sword");
		itemLocation = db.getItemLocationID("sword");
		assertEquals(0, itemLocation);//check that sword's location was updated to the user's inventory
		
		db.setItemLocation("sword", 7);//place sword back in original room
		
	}
	
	@Test
	public void testTakeItem() {
	
		db.setUserFilePath(username);
		db.setUserLocation(5);//set user's location to the room with the note
		int userLocation = db.getUserLocation();
		assertEquals(5, userLocation);//check that the user is in room 5 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 5
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 5
		item = itemsInRoom.get(0); //there should only be the note in room 5
		assertEquals("note", item.getName());//check that the item in the room is the note
		int itemLocation = db.getItemLocationID("note");
		assertEquals(5, itemLocation);//checks that the note's location is room 5, where it should be
		
		controller.gameLogic("take note");
		itemLocation = db.getItemLocationID("note");
		assertEquals(0, itemLocation);//check that the note's location was updated to the user's inventory
		
	}
	
	@Test
	public void testCantTakeItem() {
	
		db.setUserFilePath(username);
		db.setUserLocation(6);//place user in a room with no items
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);//check that the user is in room 6 now
		
		List<Item> usersInventory = db.getItemsInLocation(0);
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 6
		assertEquals(0, itemsInRoom.size());//check that there are no items in room 6
		
		controller.gameLogic("take sword");
		int itemLocation = db.getItemLocationID("sword");//location of sword should still be 19
		assertEquals(19, itemLocation);					//because the user couldn't pick it up
		
		assertTrue(usersInventory.isEmpty());//user's inventory should still be empty then
		
	}
	
	@Test
	public void testDropItem() {
	
		db.setUserFilePath(username);
		db.setUserLocation(5);//set user's location to the room with the note
		int userLocation = db.getUserLocation();
		assertEquals(5, userLocation);//check that the user is in room 5 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		int itemLocation = db.getItemLocationID("note");
		assertEquals(0, itemLocation);//checks that the note is still in the user's inventory
		
		controller.gameLogic("drop note");
		itemLocation = db.getItemLocationID("note");
		assertEquals(5, itemLocation);//check that the note was returned to room 5
		
	}
	
	@Test
	public void testPuzzleFailRoom8() {
		
		//test for user attempting to leave room 8 and enter room 17
		//user inventory is empty in this case
		//they do not have the required item to enter room 17

		db.setUserFilePath(username);
		db.setUserLocation(8);//set user's location to the room before the puzzle
		int userLocation = db.getUserLocation();
		assertEquals(8, userLocation);//check that the user is in room 8 now
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move up");
		userLocation = db.getUserLocation();
		assertEquals(8, userLocation); //user should have not been allowed to enter room 17
		
	}
	
	@Test
	public void testPuzzlePassRoom8() {
		
		//test for user attempting to enter room 8
		//this puts the required item into the user's inventory
		//which allows them to enter room 8
		
		db.setUserFilePath(username);
		db.setUserLocation(9);//set user's location to the room before the puzzle
		int userLocation = db.getUserLocation();
		assertEquals(9, userLocation);//check that the user is still in room 9
		
		db.setItemLocation("key", 0);
		int itemLocation = db.getItemLocationID("key");
		assertEquals(0, itemLocation); //check that the key is now in user's inventory
		
		controller = new GameplayController(username, false);
		controller.setModel(model);
		
		controller.gameLogic("move west");
		userLocation = db.getUserLocation();
		assertEquals(8, userLocation); //user should have been allowed to enter room 8
		
		db.setItemLocation("key", 4); //place key back in room 6 for testing purposes
		
	}
	
	@AfterClass
	public static void cleanUp() {
		
		//remove test user from database
		int user_id = db.findUserIDFromUsername("john");
		db.deleteUserFromUsersTable(user_id);
		
	}
	
}