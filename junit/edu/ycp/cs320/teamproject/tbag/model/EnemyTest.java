package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EnemyTest {

	private Agent enemy;
	private Item itemDrop;
	
	@Before
	public void setUp() {
		itemDrop = new Item(ItemType.GREENPOTION, 1, Location.ENEMY);
		enemy = new Agent(AgentName.HERCULES, 100, itemDrop, Location.RM3);
	}
	
	@Test
	public void testGetName() throws Exception {
		//test getting enemy's name
		assertEquals(AgentName.HERCULES, enemy.getName());
	}
	
	@Test
	public void testGetHealth() throws Exception {
		//test getting enemy's health
		assertEquals(100, enemy.getHealth());
	}
	
	@Test
	public void testSetHealth() throws Exception {
		//test changing enemy's health status
		int initHP = enemy.getHealth();
		enemy.setHealth(50);
		assertTrue(initHP > enemy.getHealth());
	}
	
	@Test
	public void testGetExp() throws Exception {
		//test getting experience given for defeating enemy
		assertEquals(10, enemy.getExp());
	}
	
	@Test
	public void testGetItemDrop() throws Exception {
		//test getting the enemy's item drop
		assertEquals(itemDrop, enemy.getItemDrop());
	}
	
	@Test
	public void testGetLocation() throws Exception {
		//test getting enemy's location
		assertEquals(Location.RM3, enemy.getLocation());
	}
	
	@Test
	public void testSetLocation() throws Exception {
		//test changing the enemy's current location
		Location initLoc = enemy.getLocation();
		enemy.setLocation(Location.RM1);
		assertTrue(initLoc != enemy.getLocation());
		assertTrue(initLoc == Location.RM3);
		assertTrue(enemy.getLocation() == Location.RM1);
	}
}
