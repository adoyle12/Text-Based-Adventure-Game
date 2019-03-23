package edu.ycp.cs320.teamproject.tbag.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Inventory {
	//class to represent player's inventory
	
	//reference to array list of items
	private ArrayList<Item> inventory;
	
	//reference for a specific item
	private Item item;
	private ItemType iType;
	
	//creates list for items in inventory
	public Inventory() {
		inventory = new ArrayList<Item>();
	}

	public Item getItem(int index) {
		//gets a specific item in inventory
		if(index < 0 || index > 10) { //item needs to be within inventory array
			throw new NoSuchElementException();//if outside those bounds, there is no item
		}
		return inventory.get(index);
	}
	
	public int getInventoryCap() {
		//gets inventory's capacity
		return inventory.size();
	}
	
	public void populate() {
		//populates inventory with initial items
		inventory.add(new Item(ItemType.SWORD, 1, Location.INVENTORY));
		inventory.add(new Item(ItemType.GREENPOTION, 2, Location.INVENTORY));
	}
	
	public void removeItem() {
		//removes item from inventory
		for(int i = 0; i < inventory.size(); i++) {
			item = inventory.get(i);
			if(item.getItemCount() == 0) {
				inventory.remove(i);
			}
		}
	}
}
