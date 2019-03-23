package edu.ycp.cs320.teamproject.tbag.model;

import java.util.Random;

public enum Location {
	
	//these specify locations for the player, enemies, and items
	
	//this is the initial room the player starts the game in
	RM0,
	
	RM1,
	
	RM2,
	
	RM3,
	
	RM4,
	
	RM5,
	
	RM6,
	
	RM7,
	
	RM8,
	
	INVENTORY,
	
	ENEMY;
	
	public static Location getRandomLocation() {
		Random rand = new Random();
		return values()[rand.nextInt(values().length - 2)];
	}
	
}
