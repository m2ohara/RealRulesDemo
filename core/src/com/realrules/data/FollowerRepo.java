package com.realrules.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.realrules.game.main.Game.Head;
import com.realrules.game.state.Follower;
import com.realrules.game.state.FollowerType;

public class FollowerRepo {
	
	private static FollowerRepo instance;
	private static Connection connection;
	
	private FollowerRepo() {
		connection = SqlConnection.get();
	}
	
	public static FollowerRepo get() {
		if(instance == null) {
			instance = new FollowerRepo();
		}
		try {
			if(connection == null || connection.isClosed()) {
				connection = SqlConnection.get();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public int createDB() {
		Statement createDBStatement = null;
		
		//Get sql from file
		FileHandle createDBFile = Gdx.files.internal("db//CreateDBScript.sql");
		String fileSQL = createDBFile.readString();
		
		//Initialise as false
		int result = -1;
		
		try {
			
			//Create database tables
			createDBStatement = connection.createStatement();
			result = createDBStatement.executeUpdate(fileSQL);
		}
		catch(SQLException ex) {
		//TO DO: Log error
			System.out.println("Error creating database: "+ex);
		}
		finally {
			try {
				createDBStatement.close();
				connection.close();
			}
			catch(SQLException e) {
				
			}
		}
		return result;
	}

	
//	public ResultSet getTables() {
//		ResultSet queryResult = null;
//		
//		try {
//			
//			DatabaseMetaData meta = connection.getMetaData();
//			queryResult = meta.getTables(null, null, null, new String[] {"TABLE"});
//			
//		}
//		catch(SQLException ex) {
//			System.out.println("Error getting tables: "+ex);
//		}
//		finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
//		}
//		
//		return queryResult;
//	}
	
	public ArrayList<FollowerType> getFollowerTypes() {
		
		Statement getFollowersStatement = null;
		
		//Initialise
		ResultSet result = null;
		ArrayList<FollowerType> followerTypes = new ArrayList<FollowerType>();	
		String SQL = "SELECT * FROM FOLLOWERTYPE;";
		
		
		try {
			
			//Create user
			getFollowersStatement = connection.createStatement();
			result = getFollowersStatement.executeQuery(SQL);
			
			while(result.next()) {
				String imagePath = result.getString("IMAGE_PATH");
				followerTypes.add(new FollowerType(imagePath, Head.GOSSIPER));
			}
		}
		catch(SQLException ex) {
			//TO DO: Log error
			System.out.println("Error: "+ex);
		}
		finally {
			try {
				getFollowersStatement.close();
				result.close();
				connection.close();
			}
			catch(SQLException e) {
				//TO DO: Log error
				System.out.println("Error closing statement: "+e);
			}
		}
		
		
		return followerTypes;
	}

	public ArrayList<Follower> getFollowers() {
		
		Statement getFollowersStatement = null;
		
		//Initialise
		ResultSet result = null;
		ArrayList<Follower> followers = new ArrayList<Follower>();	
		String SQL = "SELECT ft.ID, ft.IMAGE_PATH FROM FOLLOWER f JOIN FOLLOWERTYPE ft ON f.TYPE_ID = ft.ID;";
		
		
		try {
			
			//Create user
			getFollowersStatement = connection.createStatement();
			result = getFollowersStatement.executeQuery(SQL);
			
			while(result.next()) {
				int id = result.getInt("ID");
				String imagePath = result.getString("IMAGE_PATH");
				followers.add(new Follower(Head.GOSSIPER, id, imagePath));
			}
		}
		catch(SQLException ex) {
			//TO DO: Log error
			System.out.println("Error: "+ex);
		}
		finally {
			try {
				getFollowersStatement.close();
				result.close();
				connection.close();
			}
			catch(SQLException e) {
				//TO DO: Log error
				System.out.println("Error closing statement: "+e);
			}
		}
		
		
		return followers;
	}

}
