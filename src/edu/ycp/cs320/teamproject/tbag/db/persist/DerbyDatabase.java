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
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
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
	public Integer findUserIDFromUsername(final String username) 
	{
		return executeTransaction(new Transaction<Integer>() 
		{
			@Override
			public Integer execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null; 
				
				// for saving user ID
				Integer user_id = null;
				
				// try to retrieve user_ID (if it exists) from DB, for username passed into query
				try 
				{
					stmt1 = conn.prepareStatement(
							"select user_id from users " +
							"  where username = ? "
					);
					stmt1.setString(1, username);
					
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if user was found then update user_ID					
					if (resultSet1.next())
					{
						user_id = resultSet1.getInt(1);
						System.out.println("User found");	
						
					}
					else
					{
						System.out.println("User does not exist, try registering user");
						user_id = 0; 
					}
					
					return user_id; 
				
				}
				finally
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
				}
			}
				
		});
	}
	
	@Override
	public Integer getItemLocationID(String name) {
		return executeTransaction(new Transaction<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement locationID = null;
				ResultSet resultSet = null;
			try {
				locationID = connect().prepareStatement(
						" select item_location_id " +
						"  from inventory " +
						"  where inventory.item_name = ? "
				);
				locationID.setString(1, name);
				
				resultSet = locationID.executeQuery();
				
				Integer itemLocationID = null;
				
				if(resultSet.next()) {
					itemLocationID = resultSet.getInt("item_location_id");
				}
				if(itemLocationID == null) {
					System.out.println("Location of item does not exits. Error; see getItemLocation method.");
				}
				
				return itemLocationID;
			}
			finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(locationID);
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
									"insert into users (username, password, user_location_id) " +
									"  values(?, ?, 1) "
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
	
	@Override
	public Integer deleteUserFromUsersTable(final int user_id) 
	{
		return executeTransaction(new Transaction<Integer>()
		{
			@Override
			public Integer execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null; 
				
				try
				{
					stmt1 = conn.prepareStatement(
							"delete from users where user_id = ?"
							); 
					
					stmt1.setInt(1, user_id);
					stmt1.executeUpdate(); 
					System.out.println("Deleted user with ID number: " + user_id);
					return user_id; 	
				}
				
				finally
				{
					DBUtil.closeQuietly(stmt1);
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
	
		private Connection connect() throws SQLException {


			String resourcePath = null; 
			String operatingSystem = System.getProperty("os.name");
			
			if(operatingSystem.equals("Windows 10")) {
				resourcePath = "jdbc:derby:C:/TBAG.db;create=true";
			} else if(operatingSystem.equals("Mac OS X")) {
				resourcePath = "jdbc:derby:/Users/adoyle/Desktop/TBAG.db;create=true";
			} else {
				System.out.println("ACCESS DENIED: " + operatingSystem + " IS NOT A VALID OS SYSTEM");
			}
			 Connection conn = DriverManager.getConnection(resourcePath);

			
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
	
//		private void loadCommand(Gameplay gameplay, ResultSet resultSet, int index) throws SQLException{
//			gameplay.setInput(resultSet.getString(index++));
//		}
		
		//  creates the tables
		public void createTables() 
		{
			executeTransaction(new Transaction<Boolean>() 
			{
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement createLocationsStmt = null;
					PreparedStatement createInventoryStmt = null;	
					PreparedStatement createUsersStmt = null;
					PreparedStatement createJointLocationsStmt = null;
					PreparedStatement createInputsStmt = null;
				
					try {
						
						createLocationsStmt = conn.prepareStatement(
								"create table locations (" +
								"	location_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), "	+
								"	description_short varchar(4000), " +
								"	description_long varchar(8000), " +
								"	player_has_been integer" +
								")"
		
								);
						
						createLocationsStmt.executeUpdate(); 
						
						createInventoryStmt = conn.prepareStatement(
							"create table inventory ( " +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	item_location_id integer, " +
							"   item_name varchar(40) " +
							")"
						);	
						createInventoryStmt.executeUpdate();
						
						createUsersStmt = conn.prepareStatement(
							"create table users (" +
									"	user_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), "	+
									"	username varchar(20), " +
									"	password varchar(20), " +
									"	user_location_id integer constraint user_location_id references locations " +
									//"	location_id integer constraint location_id references locations "	+
									")"
						);
						createUsersStmt.executeUpdate();
						
						createJointLocationsStmt = conn.prepareStatement(
								"create table jointLocations (" +
								" 	fk_location_id integer constraint fk_location_id references locations(location_id),  " +
								" 	location_north integer, " +
								" 	location_south integer, " +
								" 	location_east integer, " +
								" 	location_west integer " +
								")"
									
							);
						createJointLocationsStmt.executeUpdate();
						
						createInputsStmt = conn.prepareStatement(
								"   create table commands (" +
								"	command_id integer primary key " +
								" 		generated always as identity (start with 1, increment by 1),  " +
								" 	command varchar(10000) " +
								")"
						);
						createInputsStmt.executeUpdate();
						
						System.out.println("tables created");				
											
						return true;
					} finally {
						DBUtil.closeQuietly(createLocationsStmt);
						DBUtil.closeQuietly(createInventoryStmt);
						DBUtil.closeQuietly(createUsersStmt);
						DBUtil.closeQuietly(createJointLocationsStmt);
						DBUtil.closeQuietly(createInputsStmt);
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
						insertLocation = conn.prepareStatement("insert into locations (description_short, description_long, player_has_been) values (?, ?, ?)" );
						for (Location location : locationList)
						{
							insertLocation.setString(1, location.getShortDescription());
							insertLocation.setString(2, location.getLongDescription());
							insertLocation.setInt(3, location.getPlayerHasBeen());
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
						
						insertItem = conn.prepareStatement("insert into inventory (item_location_id, item_name) values (?, ?)");
						for (Item item : inventory) 
						{
//							// Auto generate itemID
							insertItem.setInt(1, item.getLocationID());
							insertItem.setString(2, item.getName());
							insertItem.addBatch();
						}
						insertItem.executeBatch();
						
						insertUser = conn.prepareStatement("insert into users (username, password, user_location_id) values (?, ?, ?)");
						for(User user: userList) {
							insertUser.setString(1, user.getUsername());
							insertUser.setString(2, user.getDBPassword());
							insertUser.setInt(3, user.getLocationID());
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
			
			System.out.println("TBAG DB successfully initialized!");
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
		public Integer insertItem(String name, int locationID, int descriptionID) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Boolean addUserInput(String input) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement addInput = null;
					
					try {
						addInput = conn.prepareStatement( 
								"insert into commands (command) " +
								"  values(?) " 
						);
						addInput.setString(1, input);
						
						addInput.executeUpdate();
					}
					finally {
						DBUtil.closeQuietly(addInput);
					}
					return null;
					
				}
			});
			
		}
		
		@Override
		public Boolean addUserOutput(String output) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement addOutput = null;
					
					try {
						addOutput = conn.prepareStatement( 
								"insert into commands (command) " +
								"  values(?) " 
						);
						addOutput.setString(1, output);
						
						addOutput.executeUpdate();
					}
					finally {
						DBUtil.closeQuietly(addOutput);
					}
					return null;
					
				}
			});
			
		}

		@Override
		public ArrayList<String> getInputs() {
			return executeTransaction(new Transaction<ArrayList<String>>() {
				@Override
				public ArrayList<String> execute(Connection conn) throws SQLException {
					PreparedStatement getInputs = null;
					ResultSet resultSet = null;
					
					try {
						getInputs = conn.prepareStatement( 
								" select * from commands "
						);
						
						ArrayList<String> results = new ArrayList<String>();
						
						resultSet = getInputs.executeQuery();
						
						
						Boolean found = false;
						String command = new String();
						
						while(resultSet.next()) {
							found = true;
							
							
							command = resultSet.getString(2);
							
							results.add(command);
						}
						
						if(!found) {
							System.out.println("No commands found!");
						}
						
						return results;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getInputs);
					}
					
				}
			});
		}

		@Override
		public Integer getUserLocation(String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationID = null;
					ResultSet resultSet = null;
					
					try {
						getLocationID = conn.prepareStatement( 
								" select users.user_location_id " +
								" 	from users " +
								"	where users.username = ? "
							
						);
						getLocationID.setString(1, username);
						
						resultSet = getLocationID.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("user_location_id");
							//System.out.println(currentLocation);
						}
						
						if(currentLocation == null) {
							System.out.println("No Location Found? Where are you?");
						}
						
						return currentLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getLocationID);
					}
					
				}
			});
		}

		@Override
		public Integer setUserLocation(int location, String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement setUserLocation = null;
					PreparedStatement returnCurrentLocation = null;
					ResultSet resultSet = null;
					
					try {
						setUserLocation = conn.prepareStatement( 
								" update users " +
								" 	set user_location_id = ? " +
								"	where users.username = ? "
							
						);
						setUserLocation.setInt(1, location);
						setUserLocation.setString(2, username);
						
						setUserLocation.execute();
						
						returnCurrentLocation = conn.prepareStatement( 
								" select users.user_location_id " +
								" 	from users " +
								"	where users.username = ? "
							
						);
						returnCurrentLocation.setString(1, username);
						
						resultSet = returnCurrentLocation.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("user_location_id");
						} if(currentLocation == null) {
							System.out.println("No Location Found? Where are you?");
						}
						return currentLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(setUserLocation);
						DBUtil.closeQuietly(returnCurrentLocation);
					}
					
				}
			});
		}
		
		@Override
		public Integer pickupItem(String itemName, String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement itemLocation = null;
					PreparedStatement pickupItem = null;
					ResultSet resultSet = null;
					
					try {
						// Get user's location
						int userLocationID = getUserLocation(username);
						
						itemLocation = conn.prepareStatement( 
								" select inventory.item_location_id " +
								" 	from inventory " +
								"	where inventory.item_name = ? "
							
						);
						itemLocation.setString(1, itemName);
						
						resultSet = itemLocation.executeQuery();
						
						Integer itemLocationID = null;
						
						if(resultSet.next()) {
							itemLocationID = resultSet.getInt(1);
						}
						
						if(itemLocationID == null) {
							System.out.println("This item has no location. Error; see pickupItem method.");
						}
						
						// Check that the item is in the location of the player
						int didPickup = 0;
						if(itemLocationID == userLocationID) {
							// Move item's location to 0
							pickupItem = conn.prepareStatement( 
									" update inventory " +
									" 	set item_location_id = 0 " +
									"	where inventory.item_name = ? "
								
							);
							pickupItem.setString(1, itemName);
							
							pickupItem.executeUpdate();
							
							didPickup = 1;
						}
						
						return didPickup;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(itemLocation);
					}
					
				}
			});
		}

		@Override
		public Integer dropItem(String itemName, String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement dropItem = null;
					
					try {
						int userLocationID = getUserLocation(username);
						int itemLocationID = getItemLocationID(itemName);
						
						int didDrop = 0;
						if(itemLocationID == 0) {
							dropItem = conn.prepareStatement( 
									" update inventory " +
									" 	set item_location_id = ? " +
									"	where inventory.item_name = ? " 
							
								);
							dropItem.setInt(1, userLocationID);
							dropItem.setString(2, itemName);
						
						
							dropItem.executeUpdate();
							didDrop = 1;
						}
						
						return didDrop;
					}
					finally {
						DBUtil.closeQuietly(dropItem);
					}
					
				}
			});
		}
		
		@Override
		public String getLocationDescriptionLong(final int location_id) {
			return executeTransaction(new Transaction<String>(){
				public String execute(Connection conn) throws SQLException {
					PreparedStatement longDescription = null;
					ResultSet resultSet = null;
					try {
						longDescription = conn.prepareStatement(
								"select description_long " +
								" from locations " +
								" where location_id = ? "
						);
						longDescription.setInt(1, location_id);
					
						resultSet = longDescription.executeQuery();
					
						String result = new String();
						
						if(resultSet.next()) {
							result = resultSet.getString("description_long");
						}
					
						return result;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(longDescription);
					}
				}
			});
		}
		
		@Override
		public String getLocationDescriptionShort(final int location_id) {
			return executeTransaction(new Transaction<String>(){
				public String execute(Connection conn) throws SQLException {
					PreparedStatement shortDescription = null;
					ResultSet resultSet = null;
					try {
						shortDescription = conn.prepareStatement(
								"select description_short " +
								" from locations " +
								" where location_id = ? "
						);
						shortDescription.setInt(1, location_id);
					
						resultSet = shortDescription.executeQuery();
					
						String result = new String();
						
						if(resultSet.next()) {
							result = resultSet.getString("description_short");
						}
					
						return result;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(shortDescription);
					}
				}
			});
		}

		// TODO: This method doesn't look right
		@Override
		public Integer setItemLocation(String itemName, int location) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement setItemLocation = null;
					ResultSet resultSet = null;
					
					try {
						setItemLocation = conn.prepareStatement( 
								" update inventory " +
								" 	set item_location_id = ? " +
								" 	where inventory.item_name = ? "
							
						);
						setItemLocation.setInt(1, location);
						setItemLocation.setString(2, itemName);
						
						resultSet = setItemLocation.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("user_location_id");
							//System.out.println(currentLocation);
						}
						
						if(currentLocation == null) {
							System.out.println("No Location Found? Where are you?");
						}
						
						return currentLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(setItemLocation);
					}
					
				}
			});
		}

		@Override
		public Integer getJointLocationNorth(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getJointLocationNorth = null;
					ResultSet resultSet = null;
					
					try {
						getJointLocationNorth = conn.prepareStatement( 
								" select location_north " +
								" 	from jointLocations " +
								"	where jointLocations.fk_location_id = ? "
							
						);
						
						
						getJointLocationNorth.setInt(1, currentLocation);
						
						resultSet = getJointLocationNorth.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_north");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getJointLocationNorth);
					}	
				}
			});
		}

		@Override
		public Integer getJointLocationSouth(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getJointLocationSouth = null;
					ResultSet resultSet = null;
					
					try {
						getJointLocationSouth = conn.prepareStatement( 
								" select location_south " +
								" 	from jointLocations " +
								"	where jointLocations.fk_location_id = ? "
							
						);
						
						
						getJointLocationSouth.setInt(1, currentLocation);
						
						resultSet = getJointLocationSouth.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_south");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getJointLocationSouth);
					}	
				}
			});
		}

		@Override
		public Integer getJointLocationEast(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getJointLocationEast = null;
					ResultSet resultSet = null;
					
					try {
						getJointLocationEast = conn.prepareStatement( 
								" select location_east " +
								" 	from jointLocations " +
								"	where jointLocations.fk_location_id = ? "
							
						);
						
						
						getJointLocationEast.setInt(1, currentLocation);
						
						resultSet = getJointLocationEast.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_east");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getJointLocationEast);
					}	
				}
			});
		}

		@Override
		public Integer getJointLocationWest(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getJointLocationWest = null;
					ResultSet resultSet = null;
					
					try {
						getJointLocationWest = conn.prepareStatement( 
								" select location_west " +
								" 	from jointLocations " +
								"	where jointLocations.fk_location_id = ? "
							
						);
						
						
						getJointLocationWest.setInt(1, currentLocation);
						
						resultSet = getJointLocationWest.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_west");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getJointLocationWest);
					}	
				}
			});
		}

		@Override
		public Integer getPlayerHasBeen(int location, String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getPlayerHasBeen = null;
					ResultSet resultSet = null;
					
					try {
						getPlayerHasBeen = conn.prepareStatement( 
								" select player_has_been " +
								" 	from locations, users " +
								"	where locations.location_id = ? and users.username = ? "
							
						);
						
						
						getPlayerHasBeen.setInt(1, location);
						getPlayerHasBeen.setString(2, username);
						
						resultSet = getPlayerHasBeen.executeQuery();
						
						Integer hasBeen = null;
						
						if(resultSet.next()) {
							hasBeen = resultSet.getInt("player_has_been");
						}
						
						if(hasBeen == null) {
							System.out.println("The player never existed... Error in getPlayerHasBeen method");
						}
						
						return hasBeen;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getPlayerHasBeen);
					}	
				}
			});
		}

		@Override
		public Integer setPlayerHasBeen(int location, String username, int flag) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement setPlayerHasBeen = null;
					PreparedStatement checkPlayerHasBeen = null;
					ResultSet resultSet = null;
					
					try {
						setPlayerHasBeen = conn.prepareStatement( 
								" update locations " +
								" 	set player_has_been = ? " +
								"	where locations.location_id = ? "
						);
						
						setPlayerHasBeen.setInt(1, flag);
						setPlayerHasBeen.setInt(2, location);
						
						setPlayerHasBeen.executeUpdate();
						
						checkPlayerHasBeen = conn.prepareStatement( 
								" select player_has_been " +
								" 	from locations, users " +
								"	where locations.location_id = ? and users.username = ? "
							
						);
						
						
						checkPlayerHasBeen.setInt(1, location);
						checkPlayerHasBeen.setString(2, username);
						
						resultSet = checkPlayerHasBeen.executeQuery();
						
						Integer hasBeen = null;
						
						if(resultSet.next()) {
							hasBeen = resultSet.getInt("player_has_been");
						}
						if(hasBeen == null) {
							System.out.println("The player never existed... Error in setPlayerHasBeen method");
						}
						
						return hasBeen;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(setPlayerHasBeen);
					}	
				}
			});
		}
		
		//TODO: When should we add the item name to the description of the room
		// Should we add them to the actual description of the room OR add a string to the end of the room description each time we need to
		@Override
		public ArrayList<String> getItemNamesInLocation(String username) {
			return executeTransaction(new Transaction<ArrayList<String>>() {
				@Override
				public ArrayList<String> execute(Connection conn) throws SQLException{
					PreparedStatement getItemNames = null;
					ResultSet resultSet = null;
					
					// Get all the item names in the room that the user is in
					int userLocationID = getUserLocation(username);
					ArrayList<String> returnList = new ArrayList<String>();
					try {
						getItemNames = conn.prepareStatement( 
								" select inventory.item_name " +
								" 	from inventory " +
								"	where inventory.item_location_id = ? "
							
						);
						
						getItemNames.setInt(1, userLocationID);
						
						resultSet = getItemNames.executeQuery();
						
						Boolean found = false;
						String itemName = new String();
						
						while(resultSet.next()) {
							found = true;
							
							itemName = resultSet.getString("item_name");
							
							returnList.add(itemName);
						}
						
						if(!found) {
							System.out.println("No items found!");
						}
						
						return returnList;
						
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getItemNames);
					}
				}
			});
		}
}