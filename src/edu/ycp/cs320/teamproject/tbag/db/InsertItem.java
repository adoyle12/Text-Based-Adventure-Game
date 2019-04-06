package edu.ycp.cs320.teamproject.tbag.db;


import java.util.Scanner; 

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;

public class InsertItem {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		System.out.print("Enter the name of item: ");
		String name = keyboard.nextLine();
		
		System.out.print("Enter the locationID of the item: ");
		int roomID = keyboard.nextInt();
		
		// TODO: Figure out how to add an item with a discription
		System.out.print("Enter the descriptionID of the item: ");
		int descriptionID = keyboard.nextInt();
		
		// get the DB instance and execute the transaction
		IDatabase db = DatabaseProvider.getInstance();
		Integer itemID = db.insertItem(name, roomID, descriptionID);

		// check if the insertion succeeded
		if (itemID > 0)
		{
			System.out.println("New item (ID: " + itemID + ") successfully added to Item table: <" + name + ">");
		}
		else
		{
			System.out.println("Failed to insert new item (ID: " + itemID + ") into Item table: <" + name + ">");			
		}
	}
}
