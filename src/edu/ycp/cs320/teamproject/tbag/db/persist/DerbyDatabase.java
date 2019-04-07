/**
 * 
 */
/**
 * @author Vincent Maresca
 *
 */
package edu.ycp.cs320.teamproject.tbag.db.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.teamproject.tbag.db.persist.InitialData;
import edu.ycp.cs320.teamproject.tbag.db.persist.DBUtil;
import edu.ycp.cs320.teamproject.tbag.db.persist.DerbyDatabase;
import edu.ycp.cs320.teamproject.tbag.db.persist.PersistenceException;
import edu.ycp.cs320.teamproject.tbag.model.Description;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;
import edu.ycp.cs320.teamproject.tbag.db.model.ItemDb;

public class DerbyDatabase implements IDatabase{
	
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	@Override
	// All of the query commands go here:
	public List<ItemDb>getItemByName(String name){
		return new ArrayList<ItemDb>();
	}
	
	@Override
	public Integer insertItem(String name, int locationID, int descriptionID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
		
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
				
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
				
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	// TODO: Here is where you name and specify the location of your Derby SQL database
		// TODO: Change it here and in SQLDemo.java under CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
		// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause conflicts later w/Git
		private Connection connect() throws SQLException {

			Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/Duncan/Desktop/TBAG.db;create=true");		
//			Connection conn = DriverManager.getConnection("jdbc:derby:C:/CS320-2019-LibraryExample-DB/library.db;create=true")
			
			// Set autocommit() to false to allow the execution of
			// multiple queries/statements as part of the same transaction.
			conn.setAutoCommit(false);
			
			return conn;
		}
		
		// retrieves Item information from query result set
		private void loadItem(ItemDb item, ResultSet resultSet, int index) throws SQLException {
			item.setName(resultSet.getString(index++));
			item.setLocationID(resultSet.getInt(index++));
			item.setDescriptionID(resultSet.getInt(index++));
			item.setItemID(resultSet.getInt(index++));
		}
		
		//  creates the item table
		public void createTables() 
		{
			executeTransaction(new Transaction<Boolean>() 
			{
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt1 = null;
					PreparedStatement stmt2 = null;	
					PreparedStatement stmt3 = null;
				
					try {
						
						stmt1 = conn.prepareStatement(
								"create table locations (" +
								"	location_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), "	+
								"	description_short varchar(4000), " +
								"	description_long varchar(8000) " +
								")"
		
								);
						
						stmt1.execute(); 
						
						stmt2 = conn.prepareStatement(
							"create table inventory (" +
							"	item_id integer primary key " +
									"generated always as identity (start with 1, increment by 1), " +
							"	location_id integer constraint location_id references locations, " +
							"   item_name varchar(40) " +
							")"
						);	
						stmt2.execute();
						
						stmt3 = conn.prepareStatement(
							"create table users (" +
									"	user_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), "	+
									"	username varchar(20), " +
									"	password varchar(20) " +
									")"
						);
						stmt3.execute();
						
						System.out.println("tables created");				
											
						return true;
					} finally {
						DBUtil.closeQuietly(stmt1);
						DBUtil.closeQuietly(stmt2);
						DBUtil.closeQuietly(stmt3);
					}
				}
			});
		}
		
		// loads data retrieved from CSV files into DB tables in batch mode
		public void loadInitialData() {
			executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					List<Item> inventory;
					List<Location> locationList; 
					//List<Description> descriptionList; 
					
					try {
						inventory = InitialData.getInventory();
						locationList = InitialData.getLocations(); 
						//descriptionList = //InitialData.getDescriptions(); 
						
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertItem = null;
					PreparedStatement insertLocation = null; 

					try {
						// AD: populate locations first since location_id is foreign key in inventory table
						insertLocation = conn.prepareStatement("insert into locations (description_short, description_long) values (?, ?)" );
						for (Location location : locationList)
						{
							insertLocation.setString(1, location.getShortDescription());
							insertLocation.setString(2, location.getLongDescription());
							insertLocation.addBatch();
						}
						insertLocation.executeBatch(); 
						
						
						
						insertItem = conn.prepareStatement("insert into inventory (location_id, item_name) values (?, ?)");
						for (Item item : inventory) 
						{
//							// Auto generate itemID
							insertItem.setInt(1, item.getLocationID());
							insertItem.setString(2, item.getName());
							insertItem.addBatch();
						}
						insertItem.executeBatch();
						
						System.out.println("Item table populated");
						
						return true;
					} finally {
						DBUtil.closeQuietly(insertLocation);	
						DBUtil.closeQuietly(insertItem);					
					}
				}
			});
		}
		
		// The main method creates the database tables and loads the initial data.
		public static void main(String[] args) throws IOException {
			System.out.println("Creating tables...");
			DerbyDatabase db = new DerbyDatabase();
			db.createTables();
			
			System.out.println("Loading initial data...");
			db.loadInitialData();
			
			System.out.println("Library DB successfully initialized!");
		}
}