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
import edu.ycp.cs320.teamproject.tbag.model.JointLocations;
import edu.ycp.cs320.teamproject.tbag.model.Location;
import edu.ycp.cs320.teamproject.tbag.model.User;

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
	public Integer getItemLocationID(String name) {
		return executeTransaction(new Transaction<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement locationID = null;
				ResultSet resultSet = null;
			try {
				locationID = connect().prepareStatement(
						" select location_id " +
						"  from inventory " +
						"  where item_name = ? "
				);
				locationID.setString(1, name);
				
				resultSet = locationID.executeQuery();
				
				Item item = new Item();
				
				if(resultSet.next()) {
					item.setLocationID(resultSet.getInt(1));
				}
				return item.getLocationID();
			}
			finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(locationID);
			}
			}
		});
	}
	
	@Override
	public Location getLocationDescriptionLong(final int location_id) {
		return executeTransaction(new Transaction<Location>(){
			public Location execute(Connection conn) throws SQLException {
				PreparedStatement longDescription = null;
				ResultSet resultSet = null;
				try {
					longDescription = conn.prepareStatement(
							"select location_id, description_short, description_long " +
							" from locations " +
							" where location_id = ? "
					);
					longDescription.setInt(1, location_id);
				
					resultSet = longDescription.executeQuery();
				
					Location result = new Location();
					
					if(resultSet.next()) {
						loadLocation(result, resultSet, 1);
					}
				
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(longDescription);
				}
			}
		});
	}
	
	
	
	//transaction that inserts new user into user's table
	//if user already exists then cancel the operation
	
	@Override
	public Integer insertUserIntoUsersTable(final String username, final String password) 
	{
		return executeTransaction(new Transaction<Integer>() 
		{
			@Override
			public Integer execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null; 
				PreparedStatement stmt3 = null; 	
				
				ResultSet resultSet1 = null;	
				ResultSet resultSet3 = null; 
				
				// for saving user ID
				Integer user_id = -1;
				
				// try to retrieve user_ID (if it exists) from DB, for username passed into query
				try {
					stmt1 = conn.prepareStatement(
							"select user_id from users " +
							"  where username = ? "
					);
					stmt1.setString(1, username);
					
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if user was found then inform the user 					
					if (resultSet1.next())
					{
						user_id = -1; 
						System.out.println("Username already taken");	
						
					}
					else
					{
						System.out.println("Creating new user");
				
						// insert new user
						if (user_id <= 0) 
						{
							// prepare SQL insert statement to add user to users table
							stmt2 = conn.prepareStatement(
									"insert into users (username, password) " +
									"  values(?, ?) "
							);
							stmt2.setString(1, username);
							stmt2.setString(2, password);
							
							// execute the update
							stmt2.executeUpdate();
							
							//Get the new user's id
							stmt3 = conn.prepareStatement(
									"select user_id from users " +
											"  where username = ? "
							);
							stmt3.setString(1, username);
							
							//execute query and get result
							resultSet3 = stmt3.executeQuery(); 
							
							//should only be one value 
							resultSet3.next(); 
							user_id = resultSet3.getInt(1); 
							System.out.println("New user added");						
						}
					}
										
					
					return user_id;
				} 
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);	
				}
			}
		});
		
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
	
	//TODO: YOU MUST UNCOMMENT YOUR CONNECTION PATH 
		private Connection connect() throws SQLException {


			Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/Duncan/Desktop/TBAG.db;create=true");	
			//Connection conn = DriverManager.getConnection("jdbc:derby:/Users/adoyle/Desktop/TBAG.db;create=true");
			//Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/kille/Desktop/TBAG.db;create=true");
			//Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/jlrhi/Desktop/TBAG.db;create=true");

			
			// Set autocommit() to false to allow the execution of
			// multiple queries/statements as part of the same transaction.
			conn.setAutoCommit(false);
			
			return conn;
		}
		
		// retrieves Item information from query result set
		private void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
			item.setName(resultSet.getString(index++));
			item.setLocationID(resultSet.getInt(index++));
			item.setItemID(resultSet.getInt(index++));
		}
		
		// retrieves Item information from query result set
		private void loadLocation(Location location, ResultSet resultSet, int index) throws SQLException {
			location.setLocationID(resultSet.getInt(index++));
			location.setLongDescription(resultSet.getString(index++));
			location.setShortDescription(resultSet.getString(index++));
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
					PreparedStatement stmt4 = null;
				
					try {
						
						stmt1 = conn.prepareStatement(
								"create table locations (" +
								"	location_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), "	+
								"	description_short varchar(4000), " +
								"	description_long varchar(8000) " +
								")"
		
								);
						
						stmt1.executeUpdate(); 
						
						stmt4 = conn.prepareStatement(
							"create table jointLocations (" +
							" 	fk_location_id integer constraint fk_location_id references locations(location_id),  " +
							" 	location_north integer, " +
							" 	location_south integer, " +
							" 	location_east integer, " +
							" 	location_west integer " +
							")"
								
						);
						stmt4.executeUpdate();
						
						stmt2 = conn.prepareStatement(
							"create table inventory (" +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	location_id integer constraint location_id references locations, " +
							"   item_name varchar(40) " +
							")"
						);	
						stmt2.executeUpdate();
						
						stmt3 = conn.prepareStatement(
							"create table users (" +
									"	user_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), "	+
									"	username varchar(20), " +
									"	password varchar(20) " +
									//"	location_id integer constraint location_id references locations "	+
									")"
						);
						stmt3.executeUpdate();
						
						System.out.println("tables created");				
											
						return true;
					} finally {
						DBUtil.closeQuietly(stmt1);
						DBUtil.closeQuietly(stmt2);
						DBUtil.closeQuietly(stmt3);
						DBUtil.closeQuietly(stmt4);
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
					List<User> userList;
					List<JointLocations> jointLocationsList;
					//List<Description> descriptionList; 
					
					try {
						inventory = InitialData.getInventory();
						locationList = InitialData.getLocations(); 
						userList = InitialData.getUsers();
						jointLocationsList = InitialData.getJointLocations();
						//descriptionList = //InitialData.getDescriptions();
						
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertItem = null;
					PreparedStatement insertLocation = null; 
					PreparedStatement insertUser = null;
					PreparedStatement insertJointLocations = null;

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
						
						insertJointLocations = conn.prepareStatement("insert into jointLocations (fk_location_id, location_north, location_south, location_east, location_west) values (?, ?, ?, ?, ?)" );
						for (JointLocations jointLocations: jointLocationsList)
						{
							insertJointLocations.setInt(1, jointLocations.getLocationID());
							insertJointLocations.setInt(2, jointLocations.getLocationNorth());
							insertJointLocations.setInt(3, jointLocations.getLocationSouth());
							insertJointLocations.setInt(4, jointLocations.getLocationEast());
							insertJointLocations.setInt(5, jointLocations.getLocationWest());
							insertJointLocations.addBatch();
						}
						insertJointLocations.executeBatch();
						
						insertItem = conn.prepareStatement("insert into inventory (location_id, item_name) values (?, ?)");
						for (Item item : inventory) 
						{
//							// Auto generate itemID
							insertItem.setInt(1, item.getLocationID());
							insertItem.setString(2, item.getName());
							insertItem.addBatch();
						}
						insertItem.executeBatch();
						
						insertUser = conn.prepareStatement("insert into users (username, password) values (?, ?)");
						for(User user: userList) {
							insertUser.setString(1, user.getUsername());
							insertUser.setString(2, user.getPassword());
							insertUser.addBatch();
						}
						insertUser.executeBatch();
						
						System.out.println("Tables populated");
						
					} finally {
						DBUtil.closeQuietly(insertLocation);	
						DBUtil.closeQuietly(insertItem);
						DBUtil.closeQuietly(insertUser);
					}
					return true;
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
		
		@Override
		public String findPasswordFromUsername(String username) {
			return executeTransaction(new Transaction<String>() {
				@Override
				public String execute(Connection conn) throws SQLException {
					PreparedStatement getPassword = null;
					ResultSet resultSet = null;
					
					try {
						getPassword = conn.prepareStatement( 
								" select password from users " +
								" where users.username = ? " 
						);
						getPassword.setString(1, username);
						
						resultSet = getPassword.executeQuery();
						
						String password = null;
						
						if(resultSet.next()) {
							password = resultSet.getString(1);
						}
						
						System.out.println(password);
						return password;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getPassword);
					}
					
				}
			});
		}

		@Override
		public Integer getLocationID() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Integer insertItem(String name, int locationID, int descriptionID) {
			// TODO Auto-generated method stub
			return null;
		}
}