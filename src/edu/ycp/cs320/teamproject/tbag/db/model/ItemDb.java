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
	private int roomID;
	private int descriptionID;
	
	public ItemDb() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	public int getRoomID() {
		return this.roomID;
	}
	
	public void setDescriptionID(int descriptionID) {
		this.descriptionID = descriptionID;
	}
	
	public int getDescriptionID() {
		return this.descriptionID;
	}
	
}