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

import edu.ycp.cs320.teamproject.tbag.db.persist.DatabaseProvider;
import edu.ycp.cs320.teamproject.tbag.db.persist.IDatabase;
import edu.ycp.cs320.teamproject.tbag.model.Location;

public class getLocationDescriptionLong {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		String returnString = "Empty";
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Location location = new Location();
		location = db.getLocationDescriptionLong(3);
		returnString = location.getLongDescription();
		
		// check if anything was returned and output the list
		if (returnString.equals("Empty")) {
			System.out.println("No returned id");
		}
		else {		
				System.out.println("Long discription is: " + returnString);
		}
	}
}