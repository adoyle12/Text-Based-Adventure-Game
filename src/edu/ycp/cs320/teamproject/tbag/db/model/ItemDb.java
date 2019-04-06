/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db.model;

// This is used for the models for the fake database

public class ItemDb {
	private String name;
	private int locationID;
	private int descriptionID;
	private int itemID;
	
	public ItemDb() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	
	public int getLocationID() {
		return this.locationID;
	}
	
	public void setDescriptionID(int descriptionID) {
		this.descriptionID = descriptionID;
	}
	
	public int getDescriptionID() {
		return this.descriptionID;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public int getItemID() {
		return this.itemID;
	}
	
}