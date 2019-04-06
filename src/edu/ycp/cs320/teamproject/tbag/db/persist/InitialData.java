/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;

public class InitialData {
	
	public static List<ItemDb> getItem() throws IOException{
		List<ItemDb> itemList = new ArrayList<ItemDb>();
		ReadCSV readItems = new ReadCSV("item.csv");
			
		try {
			while (true) {
				List<String> tuple = readItems.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				ItemDb item = new ItemDb();
			
				Integer.parseInt(i.next());
				item.setName(i.next());				
				item.setRoomID(Integer.parseInt(i.next()));
				item.setDescriptionID(Integer.parseInt(i.next()));
				itemList.add(item);
			}
			System.out.println("itemList loaded from CSV file");
			return itemList;
		} finally {
			readItems.close();
		}
	}
}