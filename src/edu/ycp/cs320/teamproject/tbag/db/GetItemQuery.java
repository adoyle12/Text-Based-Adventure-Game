/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;
import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;

public class GetItemQuery {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		System.out.print("Enter a name of an item: ");
		String name = keyboard.nextLine();
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		List<ItemDb> itemList = db.getItemByName(name);
		
		// check if anything was returned and output the list
		if (itemList.isEmpty()) {
			System.out.println("No item found with name <" + name + ">");
		}
		else {
			for (ItemDb item : itemList) {
				System.out.println(item.getName() + "," + item.getLocationID() + "," + item.getDescriptionID());
			}
		}
	}
}