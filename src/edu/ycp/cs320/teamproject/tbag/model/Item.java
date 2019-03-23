package edu.ycp.cs320.teamproject.tbag.model;

public class Item {
	
	//reference for item type
	private ItemType type;
	
	//reference for number of item held
	private int count;
	
	//reference for item's location
	private Location itemLoc;
	
	public Item(ItemType type, int count, Location itemLoc) {
		//initialize item state
		this.type = type;
		this.count = count;
		this.itemLoc = itemLoc;
	}
	
	public ItemType getItemType() {
		//get type of item
		return type;
	}
	
	public int getItemCount() {
		//get # of a particular item held
		return count;
	}
	
	public void setItemCount(int num) {
		//change the # of a particular item held
		this.count = num;
	}
	
	public Location getItemLocation() {
		//get item's current location
		return itemLoc;
	}
	
	public void setItemLocation(Location iLoc) {
		//set the item's location
		this.itemLoc = iLoc;
	}

	public static Item getRandomItemDrop() {
		//get random item for item drops by enemies
		return new Item(ItemType.getRandomItemType(), 1, Location.ENEMY);
	}
	
	public static Item getRandomItem() {
		//get random item for items to be found in labyrinth
		return new Item(ItemType.getRandomItemType(), 1, Location.getRandomLocation());
	}
}
