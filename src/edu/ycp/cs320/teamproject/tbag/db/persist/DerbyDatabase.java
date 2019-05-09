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
import edu.ycp.cs320.teamproject.tbag.model.Agent;
import edu.ycp.cs320.teamproject.tbag.model.Gameplay;
import edu.ycp.cs320.teamproject.tbag.model.Item;
import edu.ycp.cs320.teamproject.tbag.model.Location;
import edu.ycp.cs320.teamproject.tbag.model.Puzzle;
import edu.ycp.cs320.teamproject.tbag.model.User;

public class DerbyDatabase implements IDatabase{
	
	String userFilePath = null; 
	
	Boolean loggedIn = false; 
	
	public void setUserFilePath(String userFilePath)
	{
		this.userFilePath = userFilePath; 
	}
	
	
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
				locationID = conn.prepareStatement(
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
		setUserFilePath("users"); 
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
									"insert into users (username, password) " +	//, user_location_id
									"  values(?, ?) "					//, 1
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
							
							System.out.println("Creating new user's db"); 
							
							//Create the new users db
							DerbyDatabase db = new DerbyDatabase();
							db.setUserFilePath(username);
							db.createGameTables(username);
							db.loadInitialData();
							
						
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
	
		private Connection connect() throws SQLException 
		{
//			String username = null; 
//			System.out.println(loggedInUsername); 
//			if (loggedInUsername != null && loggedIn == true)
//			{
//				username = loggedInUsername; 
//			}
//			else 
//			{
//				
//			}
			//String username = user.getUsername(); 	//AD + DS: Used for setting a different file path for each user
			
			System.out.println(userFilePath);
			String resourcePath = null; 
			String operatingSystem = System.getProperty("os.name");
			
			if(operatingSystem.equals("Windows 10")) {
				resourcePath = "jdbc:derby:C:/TBAG_DBs/" + userFilePath + "TBAG.db;create=true";
			} else if(operatingSystem.equals("Mac OS X")) {
				resourcePath = "jdbc:derby:/Users/adoyle/Desktop/" + userFilePath + "TBAG.db;create=true";
			} else {
				System.out.println("ACCESS DENIED: " + operatingSystem + " IS NOT A VALID OS SYSTEM");
			}
			System.out.println(resourcePath); 
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
		
		
		public void createUsersTable()
		{
			setUserFilePath("users"); 
			executeTransaction(new Transaction<Boolean>() 
			{
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement createUsersStmt = null;
					
					
				
					try {
						
						
						createUsersStmt = conn.prepareStatement(
							"create table users (" +
									"	user_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), "	+
									"	username varchar(20), " +
									"	password varchar(20)" +
								//	"	user_location_id integer constraint user_location_id references locations " +
									//"	location_id integer constraint location_id references locations "	+
									")"
						);
						createUsersStmt.executeUpdate();
						
						System.out.println("User table created");				
											
						return true;
					} finally {
						DBUtil.closeQuietly(createUsersStmt);
					}
				}
			});
		}
		
		//  creates the tables
		public void createGameTables(String username) 
		{
			
			executeTransaction(new Transaction<Boolean>() 
			{
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement createLocationsStmt = null;
					PreparedStatement createInventoryStmt = null;	
					PreparedStatement createInputsStmt = null;
					PreparedStatement createGameStateStmt = null; 
					PreparedStatement createAgentsStmt = null;
					PreparedStatement createPuzzleStmt = null;
					
				
					try {
						
						createLocationsStmt = conn.prepareStatement(
								"create table locations (" +
								"	location_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), "	+
								"	description_short varchar(4000), " +
								"	description_long varchar(8000), " +
								"	player_has_been integer," +
								" 	location_north integer, " +
								" 	location_south integer, " +
								" 	location_east integer, " +
								" 	location_west integer, " +
								" 	location_up integer, " +
								" 	location_down integer " +
								")"
		
								);
						
						createLocationsStmt.executeUpdate(); 
						
						createInventoryStmt = conn.prepareStatement(
							"create table inventory ( " +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	item_location_id integer, " +
							"   item_name varchar(40), " +
							"	item_description varchar(8000), " +
							"	on_user_flag integer " +
							")"
						);	
						createInventoryStmt.executeUpdate();
						

						createAgentsStmt = conn.prepareStatement(
								"create table agents (" +
								" agent_id integer primary key " +
								" generated always as identity (start with 1, increment by 1), " +
								" agent_location_id integer, " +
								" agent_description varchar(8000) " +
								")"
								);
						
						createAgentsStmt.executeUpdate();
						
						createInputsStmt = conn.prepareStatement(
								"   create table commands (" +
								"	command_id integer primary key " +
								" 		generated always as identity (start with 1, increment by 1),  " +
								" 	command varchar(10000) " +
								")"
						);
						createInputsStmt.executeUpdate();
						
						createPuzzleStmt = conn.prepareStatement(
								"   create table puzzle (" +
								"	puzzle_location_id integer, " +
								" 	item_name varChar(100)  " +
								")"
						);
						createPuzzleStmt.executeUpdate();
						
						//AD: should items be in game state or do we have a way to know what items the user has already?
						createGameStateStmt = conn.prepareStatement(
								"	create table gameState (" +
								"	game_id integer primary key " +
								"	generated always as identity (start with 1, increment by 1), " +
								"	location_id integer, " +
								"	health integer, " +
								" 	score integer " +
								")"	
								);
						createGameStateStmt.executeUpdate(); 
						
						System.out.println("Game tables created");				
											
						return true;
					} finally {
						DBUtil.closeQuietly(createLocationsStmt);
						DBUtil.closeQuietly(createInventoryStmt);
						DBUtil.closeQuietly(createInputsStmt);
						DBUtil.closeQuietly(createGameStateStmt);
						DBUtil.closeQuietly(createAgentsStmt);
						DBUtil.closeQuietly(createPuzzleStmt);
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
					List<Agent> agentList;
					List<Puzzle> puzzleList;
					
					try {
						inventory = InitialData.getInventory();
						locationList = InitialData.getLocations(); 
						agentList = InitialData.getAgents();
						puzzleList = InitialData.getPuzzle();
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					PreparedStatement insertItem = null;
					PreparedStatement insertLocation = null; 
					PreparedStatement insertGameState = null; //This is the hardcoded initial game state - don't need to read from CSV
					PreparedStatement insertAgents = null;
					PreparedStatement insertPuzzle = null;

					try {
						// AD: populate locations first since location_id is foreign key in inventory table
						insertLocation = conn.prepareStatement("insert into locations (description_short, description_long, player_has_been, location_north, location_south, location_east, location_west, location_up, location_down) values (?, ?, ?, ?, ?, ?, ?, ?, ?)" );
						for (Location location : locationList)
						{
							insertLocation.setString(1, location.getShortDescription());
							insertLocation.setString(2, location.getLongDescription());
							insertLocation.setInt(3, location.getPlayerHasBeen());
							insertLocation.setInt(4, location.getLocationNorth());
							insertLocation.setInt(5, location.getLocationSouth());
							insertLocation.setInt(6, location.getLocationEast());
							insertLocation.setInt(7, location.getLocationWest());
							insertLocation.setInt(8, location.getLocationUp());
							insertLocation.setInt(9, location.getLocationDown());
							
							insertLocation.addBatch();
						}
						insertLocation.executeBatch(); 
						
						
						insertItem = conn.prepareStatement("insert into inventory (item_location_id, item_name, item_description, on_user_flag) values (?, ?, ?, ?)");
						for (Item item : inventory) 
						{
//							// Auto generate itemID
							insertItem.setInt(1, item.getLocationID());
							insertItem.setString(2, item.getName());
							insertItem.setString(3, item.getItemDescription());
							insertItem.setInt(4, item.getOnUserFlag());
							insertItem.addBatch();
						}
						insertItem.executeBatch();
						
						//inserts agents
						insertAgents = conn.prepareStatement("insert into agents (agent_location_id, agent_description) values (?, ?)");
						for(Agent agent: agentList) {
							insertAgents.setInt(1, agent.getLocationID());
							insertAgents.setString(2, agent.getAgentDescription());
							insertAgents.addBatch();
						}
						insertAgents.executeBatch();
						
						insertPuzzle = conn.prepareStatement("insert into puzzle (puzzle_location_id, item_name) values (?, ?)");
						for (Puzzle puzzle : puzzleList) 
						{
							insertPuzzle.setInt(1, puzzle.getLocationID());
							insertPuzzle.setString(2, puzzle.getName());
							insertPuzzle.addBatch();
						}
						insertPuzzle.executeBatch();
						
						insertGameState = conn.prepareStatement("insert into gameState (location_id, health, score) values (1, 100, 0)"); 
						insertGameState.executeUpdate(); 
						
						System.out.println("Tables populated");
						
					} finally {
						DBUtil.closeQuietly(insertLocation);	
						DBUtil.closeQuietly(insertItem);
						DBUtil.closeQuietly(insertAgents);
						DBUtil.closeQuietly(insertPuzzle);
					}
					return true;
				}
			});
		}
		
		// The main method creates the database tables and loads the initial data.
		public static void main(String[] args) throws IOException {
			System.out.println("Creating Users table...");
			DerbyDatabase db = new DerbyDatabase();
			db.createUsersTable();
			
//			System.out.println("Loading initial data...");
//			db.loadInitialData();
			
			System.out.println("DB successfully initialized!");
		}
		
		@Override
		public String findPasswordFromUsername(String username) 
		{
				setUserFilePath("users"); 
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
		public Boolean addUserInput(String input) 
		{
			
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
		public Integer getUserLocation() {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationID = null;
					ResultSet resultSet = null;
					
					try {
						getLocationID = conn.prepareStatement( 
								" select location_id " +
								" 	from gameState "
							
						);
						
						resultSet = getLocationID.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("location_id");
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
		public Integer setUserLocation(int location) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement setUserLocation = null;
					PreparedStatement returnCurrentLocation = null;
					ResultSet resultSet = null;
					
					try {
						setUserLocation = conn.prepareStatement( 
								" update gameState " +
								" 	set location_id = ? " 
							
						);
						setUserLocation.setInt(1, location);
						
						setUserLocation.execute();
						
						returnCurrentLocation = conn.prepareStatement( 
								" select location_id " +
								" 	from gameState "
						);
						
						resultSet = returnCurrentLocation.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("location_id");
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
		public Integer getAgentLocation(int agent_id) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationID = null;
					ResultSet resultSet = null;
					
					try {
						getLocationID = conn.prepareStatement( 
								" select agents.agent_location_id " +
								" 	from agents " +
								"	where agent_id = ? "
							
						);
						getLocationID.setInt(1, agent_id);
						
						resultSet = getLocationID.executeQuery();
						
						Integer agentLocation = null;
						
						if(resultSet.next()) {
							agentLocation = resultSet.getInt("agent_location_id");
						}
						
						return agentLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getLocationID);
					}
					
				}
			});
		}
		
		@Override
		public String getAgentDescription(final int agent_location_id) {
			return executeTransaction(new Transaction<String>(){
				public String execute(Connection conn) throws SQLException {
					PreparedStatement agentDescription = null;
					ResultSet resultSet = null;
					try {
						agentDescription = conn.prepareStatement(
								"select agents.agent_description " +
								" from agents " +
								" where agent_location_id = ? "
						);
						agentDescription.setInt(1, agent_location_id);
					
						resultSet = agentDescription.executeQuery();
					
						String result = new String();
						
						if(resultSet.next()) {
							result = resultSet.getString("agent_description");
						}
					
						return result;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(agentDescription);
					}
				}
			});
		}
		
		@Override
		public String getItemDescription(final int item_location, final int onUserFlag) {
			return executeTransaction(new Transaction<String>(){
				public String execute(Connection conn) throws SQLException {
					PreparedStatement itemDescription = null;
					ResultSet resultSet = null;
					try {
						itemDescription = conn.prepareStatement(
								"select inventory.item_description " +
								" from inventory " +
								" where inventory.item_location_id = ? " +
								" and inventory.on_user_flag = ?"
						);
						itemDescription.setInt(1, item_location);
						itemDescription.setInt(2, onUserFlag);
					
						resultSet = itemDescription.executeQuery();
					
						String result = new String();
						
						if(resultSet.next()) {
							result = resultSet.getString("item_description");
						}
					
						return result;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(itemDescription);
					}
				}
			});
		}
		
		@Override
		public Integer setItemLocation(String itemName, int location, int onUserFlag) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement setItemLocation = null;
					ResultSet resultSet = null;
					
					try {
						setItemLocation = conn.prepareStatement( 
								" update inventory " +
								" 	set item_location_id = ? " +
								" 	where inventory.item_name = ? " +
								"	and inventory.onUserFlag = ?"
							
						);
						setItemLocation.setInt(1, location);
						setItemLocation.setString(2, itemName);
						setItemLocation.setInt(3, onUserFlag);
						
						resultSet = setItemLocation.executeQuery();
						
						Integer currentLocation = null;
						
						if(resultSet.next()) {
							currentLocation = resultSet.getInt("user_location_id");
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
		public Integer pickupItem(String itemName, String username) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement itemLocation = null;
					PreparedStatement pickupItem = null;
					ResultSet resultSet = null;
					
					try {
						// Get user's location
						int userLocationID = getUserLocation();
						
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
						int userLocationID = getUserLocation();
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

		@Override
		public Integer getLocationNorth(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationNorth = null;
					ResultSet resultSet = null;
					
					try {

						getLocationNorth = conn.prepareStatement( 
								" select location_north " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationNorth.setInt(1, currentLocation);
						
						resultSet = getLocationNorth.executeQuery();
						
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
						DBUtil.closeQuietly(getLocationNorth);
					}	
				}
			});
		}

		@Override
		public Integer getLocationSouth(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationSouth = null;
					ResultSet resultSet = null;
					
					try {
						getLocationSouth = conn.prepareStatement( 
								" select location_south " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationSouth.setInt(1, currentLocation);
						
						resultSet = getLocationSouth.executeQuery();
						
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
						DBUtil.closeQuietly(getLocationSouth);
					}	
				}
			});
		}

		@Override
		public Integer getLocationEast(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationEast = null;
					ResultSet resultSet = null;
					
					try {
						getLocationEast = conn.prepareStatement( 
								" select location_east " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationEast.setInt(1, currentLocation);
						
						resultSet = getLocationEast.executeQuery();
						
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
						DBUtil.closeQuietly(getLocationEast);
					}	
				}
			});
		}

		@Override
		public Integer getLocationWest(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationWest = null;
					ResultSet resultSet = null;
					
					try {
						getLocationWest = conn.prepareStatement( 
								" select location_west " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationWest.setInt(1, currentLocation);
						
						resultSet = getLocationWest.executeQuery();
						
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
						DBUtil.closeQuietly(getLocationWest);
					}	
				}
			});
		}
		
		@Override
		public Integer getLocationUp(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationUp = null;
					ResultSet resultSet = null;
					
					try {
						getLocationUp = conn.prepareStatement( 
								" select location_up " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationUp.setInt(1, currentLocation);
						
						resultSet = getLocationUp.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_up");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getLocationUp);
					}	
				}
			});
		}
		
		@Override
		public Integer getLocationDown(int currentLocation) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getLocationDown = null;
					ResultSet resultSet = null;
					
					try {
						getLocationDown = conn.prepareStatement( 
								" select location_down " +
								" 	from locations " +
								"	where location_id = ? "
							
						);
						
						
						getLocationDown.setInt(1, currentLocation);
						
						resultSet = getLocationDown.executeQuery();
						
						Integer nextLocation = null;
						
						if(resultSet.next()) {
							nextLocation = resultSet.getInt("location_down");
						}
						
						if(nextLocation == null) {
							System.out.println("Next room not found");
						}
						
						return nextLocation;
					}
					finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(getLocationDown);
					}	
				}
			});
		}

		@Override
		public Integer getPlayerHasBeen(int location) {
			return executeTransaction(new Transaction<Integer>() {
				@Override
				public Integer execute(Connection conn) throws SQLException {
					PreparedStatement getPlayerHasBeen = null;
					ResultSet resultSet = null;
					
					try {
						getPlayerHasBeen = conn.prepareStatement( 
								" select player_has_been " +
								" 	from locations " +
								"	where locations.location_id = ? "
							
						);
						
						
						getPlayerHasBeen.setInt(1, location);
						
						
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
		public Integer setPlayerHasBeen(int location, int flag) {
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
								" 	from locations " +
								"	where locations.location_id = ? "
							
						);
						
						
						checkPlayerHasBeen.setInt(1, location);
						
						
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

		@Override
		public Integer insertItem(String name, int locationID, int descriptionID) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getPuzzleItemName(int location_id) {
			// TODO Auto-generated method stub
			return null;
		}

}
