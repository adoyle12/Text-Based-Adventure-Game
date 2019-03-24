package edu.ycp.cs320.teamproject.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	
	private Item sword;
	private Item dagger;
	private Item shield;
	private Item grnPotion;
	
	@Before
	public void setUp() {
		sword = new Item(ItemType.SWORD, 1, Location.RM1);
		dagger = new Item(ItemType.DAGGER, 0, Location.RM5);
		shield = new Item(ItemType.SHIELD, 0, Location.RM0);
		grnPotion = new Item(ItemType.GREENPOTION, 5, Location.RM8);
	}

	@Test
	public void testGetItemType() throws Exception {
		//test getting item type
		assertEquals(ItemType.SWORD, sword.getItemType());
		assertEquals(ItemType.DAGGER, dagger.getItemType());
		assertEquals(ItemType.SHIELD, shield.getItemType());
		assertEquals(ItemType.GREENPOTION, grnPotion.getItemType());
	}
	
	@Test
	public void testGetItemCount() throws Exception {
		//test getting the number of items held
		assertEquals(1, sword.getItemCount());
		assertEquals(0, dagger.getItemCount());
		assertEquals(0, shield.getItemCount());
		assertEquals(5, grnPotion.getItemCount());
	}

	@Test
	public void testSetItemCount() throws Exception {
		//test changing the number of items held
		int initSwordCount = sword.getItemCount();
		sword.setItemCount(2);
		assertEquals(1, initSwordCount);
		assertTrue(initSwordCount < sword.getItemCount());
		assertEquals(2, sword.getItemCount());
		
		int initDaggerCount = dagger.getItemCount();
		dagger.setItemCount(1);
		assertEquals(0, initDaggerCount);
		assertTrue(initDaggerCount < dagger.getItemCount());
		assertEquals(1, dagger.getItemCount());
		
		int initShieldCount = shield.getItemCount();
		shield.setItemCount(1);
		assertEquals(0, initShieldCount);
		assertTrue(initShieldCount < shield.getItemCount());
		assertEquals(1, shield.getItemCount());
		
		int initGPCount = grnPotion.getItemCount();
		grnPotion.setItemCount(99);
		assertEquals(5, initGPCount);
		assertTrue(initGPCount < grnPotion.getItemCount());
		assertEquals(99, grnPotion.getItemCount());
	}
	
	@Test
	public void testGetItemLocation() throws Exception {
		//test getting item's current location
		Location swordLoc = sword.getItemLocation();
		Location daggerLoc = dagger.getItemLocation();
		Location shieldLoc = shield.getItemLocation();
		Location potionLoc = grnPotion.getItemLocation();
		
		assertEquals(Location.RM1, swordLoc);
		assertEquals(Location.RM5, daggerLoc);
		assertEquals(Location.RM0, shieldLoc);
		assertEquals(Location.RM8, potionLoc);
	}
	
	@Test
	public void testSetItemLocation() throws Exception {
		//test changing item's location
		Location swordILoc = sword.getItemLocation();
		Location daggerILoc = dagger.getItemLocation();
		Location shieldILoc = shield.getItemLocation();
		Location potionILoc = grnPotion.getItemLocation();
		
		sword.setItemLocation(Location.RM2);
		dagger.setItemLocation(Location.RM4);
		shield.setItemLocation(Location.RM1);
		grnPotion.setItemLocation(Location.RM7);
		
		assertTrue(swordILoc != sword.getItemLocation());
		assertTrue(daggerILoc != dagger.getItemLocation());
		assertTrue(shieldILoc != shield.getItemLocation());
		assertTrue(potionILoc != grnPotion.getItemLocation());
		
		assertEquals(Location.RM2, sword.getItemLocation());
		assertEquals(Location.RM4, dagger.getItemLocation());
		assertEquals(Location.RM1, shield.getItemLocation());
		assertEquals(Location.RM7, grnPotion.getItemLocation());
	}
	
	@Test
	public void testGetRandomItemDrop() throws Exception {
		//test getting random item drop from enemy
		Item itemDrop = Item.getRandomItemDrop();
		Item difDrop = Item.getRandomItemDrop();
		
		assertEquals(1, itemDrop.getItemCount());
		assertEquals(Location.ENEMY, itemDrop.getItemLocation());
		assertTrue(Location.getRandomLocation() != itemDrop.getItemLocation());
		assertTrue(itemDrop != difDrop);
	}
	
	@Test
	public void testGetRandomItem() throws Exception {
		//test getting a random item for items to be found
		Item randItem = Item.getRandomItem();
		Item difItem = Item.getRandomItem();
		
		assertTrue(randItem != difItem);
		assertTrue(randItem.getItemLocation() != Location.ENEMY && randItem.getItemLocation() != Location.INVENTORY);
		assertTrue(difItem.getItemLocation() != Location.ENEMY && difItem.getItemLocation() != Location.INVENTORY);
		assertTrue(randItem.getItemLocation() != difItem.getItemLocation());
		assertTrue(randItem.getItemCount() == 1 && difItem.getItemCount() == 1);
	}
}
