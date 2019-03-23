package edu.ycp.cs320.teamproject.tbag.model;

import java.util.Random;

public enum ItemType {

	//for items, more can be added later
	SWORD,
	
	DAGGER,
	
	SHIELD,
	
	GREENPOTION;
	
	//get random item
	public static ItemType getRandomItemType() {
		Random rand = new Random();
		return values()[rand.nextInt(values().length)];
	}
	
}
