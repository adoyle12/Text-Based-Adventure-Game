package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

private Player player;
	
	@Before
	public void setUp() {
		player = new Player(100, Location.RM0, 0);
	}
	
	@Test
	public void testGetHealth() throws Exception {
		//test getting player's health
		assertEquals(100, player.getHealth());
	}
	
	@Test
	public void testSetHealth() throws Exception {
		//test changing player's health status
		int initHP = player.getHealth();
		player.setHealth(50);
		assertTrue(initHP > player.getHealth());
	}

	@Test
	public void testGetScore() throws Exception {
		//test getting player's score
		assertEquals(0, player.getScore());
	}
	
	@Test
	public void testSetScore() throws Exception {
		//test changing the player's current score
		int initScore = player.getScore();
		player.setScore(10);
		assertTrue(initScore < player.getScore());
		assertEquals(10, player.getScore());
	}
	
	@Test
	public void testGetLocation() throws Exception {
		//test getting player's location
		assertEquals(Location.RM0, player.getLocation());
	}
	
	@Test
	public void testSetLocation() throws Exception {
		//test changing the player's current location
		Location initLoc = player.getLocation();
		player.setLocation(Location.RM1);
		assertTrue(initLoc != player.getLocation());
		assertTrue(initLoc == Location.RM0);
		assertTrue(player.getLocation() == Location.RM1);
	}

}
