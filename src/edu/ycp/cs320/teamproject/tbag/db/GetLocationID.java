/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db;

import java.util.Scanner;

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;

public class GetLocationID {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		System.out.print("Enter a name of an item: ");
		String name = keyboard.nextLine();
		
		Integer returnInt = -1;
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		returnInt = db.getItemLocationID(name);
		
		// check if anything was returned and output the list
		if (returnInt == -1) {
			System.out.println("No returned id");
		}
		else {		
				System.out.println("Location Id is: " + returnInt);
		}
	}
}