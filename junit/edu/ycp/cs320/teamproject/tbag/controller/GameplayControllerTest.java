package edu.ycp.cs320.teamproject.tbag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
	private Item item;
	private IDatabase db;
	
	@Test
	public void testInput()
	{
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		
		controller = new GameplayController("john");
		
		controller.setModel(model);
		
		controller.gameLogic("Test");
		assertEquals("Test", model.getInput()); 
	}
	
	@Test
	public void testGoNorth() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(5);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(7);
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("go south");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	public void testGoEast() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(3);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(3);

		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(1);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(6);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(5);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(7);
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("move south");
		int userLocation = db.getUserLocation();
		assertEquals(10, userLocation);
		
	}
	
	public void testMoveEast() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(3);
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("move east");
		int userLocation = db.getUserLocation();
		assertEquals(4, userLocation);
		
	}
	
	@Test
	public void testMoveWest() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(3);

		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(1);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(6);
		
		controller = new GameplayController("john");
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
		user = new User();
		user.setUsername("john");
		
		db.setUserFilePath("john");
		db.setUserLocation(6);//set user's location to the room with the sword
		int userLocation = db.getUserLocation();
		assertEquals(6, userLocation);//check that the user is in room 6 now
		
		List<Item> usersInventory = db.getItemsInLocation(0);//get user's inventory
		assertEquals(0, usersInventory.size());//check that user has nothing in their inventory
		
		List<Item> itemsInRoom = db.getItemsInLocation(db.getUserLocation());//get the items in room 6
		assertEquals(1, itemsInRoom.size());//check that there is only one item in room 6
		item = itemsInRoom.get(0); //there should only be a sword in room 6
		assertEquals("sword", item.getName());//check that the item in the room is a sword
		int itemLocation = db.getItemLocationID("sword");
		assertEquals(6, itemLocation);//checks that the sword's location is room 6, where it should be
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("pick up sword");
		itemLocation = db.getItemLocationID("sword");
		assertEquals(0, itemLocation);//check that sword's location was updated to being on the user
		assertEquals(1, usersInventory.size());//check that an item was added to the user's inventory
		
	}
	
	@Test
	public void testGrabItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		item = new Item();
		item.setName("sword");
		
		db.setUserFilePath("john");
		db.setUserLocation(6);
		db.setItemLocation(item.getName(), 6);
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("grab" + item);
		assertEquals(0, item.getLocationID());
		
	}
	
	@Test
	public void testTakeItem() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		model = new Gameplay();
		user = new User();
		user.setUsername("john");
		
		item = new Item();
		item.setName("sword");
		
		db.setUserFilePath("john");
		db.setUserLocation(6);
		db.setItemLocation(item.getName(), 6);
		
		controller = new GameplayController("john");
		controller.setModel(model);
		
		controller.gameLogic("take" + item);
		assertEquals(0, item.getLocationID());
		
	}
}
