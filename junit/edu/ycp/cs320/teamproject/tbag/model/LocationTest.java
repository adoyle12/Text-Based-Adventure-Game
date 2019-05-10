package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
	
	private Location model;
	private String shortDescription;
	private String longDescription;
	private Integer hasBeen;
	
	@Before
	public void setUp() {
		model = new Location();
		model.setLocationID(1);//set locationID
		model.setShortDescription(shortDescription);//set short description
		model.setLongDescription(longDescription);//set long description
		model.setPlayerHasBeen(0);//set playerHasBeen flag
		model.setLocationNorth(2);//set location north
		model.setLocationSouth(3);//set location south
		model.setLocationEast(4);//set location east
		model.setLocationWest(5);//set location west
		model.setLocationUp(10);//set location up
		model.setLocationDown(11);//set location down
	}
	
	@Test
	public void testGetSetLocationID() {
		//test that location ID was set correctly
		assertEquals(1, model.getLocationID());
		
		//test that location ID was changed to 2
		model.setLocationID(2);
		assertEquals(2, model.getLocationID());
	}
	
	@Test
	public void testGetSetShortLongDescription() {
		//test that short description was set correctly
		assertEquals(shortDescription, model.getShortDescription());
		
		//test that long description was set correctly
		assertEquals(longDescription, model.getLongDescription());
	}
	
	@Test
	public void testGetSetPlayerHasBeen() {
		//test that hasBeen was set correctly
		assertEquals(0, model.getPlayerHasBeen());
		
		//test that hasBeen has been changed to 1
		model.setPlayerHasBeen(1);
		assertEquals(1, model.getPlayerHasBeen());
	}
	
	@Test
	public void testNorthSouthEastWestUpDown() {
		//test that north, south, east, and west locations were set correctly
		assertEquals(2, model.getLocationNorth());
		assertEquals(3, model.getLocationSouth());
		assertEquals(4, model.getLocationEast());
		assertEquals(5, model.getLocationWest());
		assertEquals(10, model.getLocationUp());
		assertEquals(11, model.getLocationDown());
		
		//test that n, s, e and w were changed correctly
		model.setLocationNorth(6);
		model.setLocationSouth(7);
		model.setLocationEast(8);
		model.setLocationWest(9);
		model.setLocationUp(12);
		model.setLocationDown(13);
		assertEquals(6, model.getLocationNorth());
		assertEquals(7, model.getLocationSouth());
		assertEquals(8, model.getLocationEast());
		assertEquals(9, model.getLocationWest());
		assertEquals(12, model.getLocationUp());
		assertEquals(13, model.getLocationDown());
	}
}
