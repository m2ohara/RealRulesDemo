package com.realrules.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FollowerRepo {
	
	private static FollowerRepo instance;
	private static Connection connection;
	
	private FollowerRepo() {
		connection = SqlConnection.get();
	}
	
	public static FollowerRepo getInstance() {
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

	public int insert() {
		Statement createDBStatement = null;
		
		//Get sql from file
		FileHandle createDBFile = Gdx.files.internal("createDB.sql");
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

	public int update() {
		// TODO Auto-generated method stub
		return -1;
	}

	public int delete() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	public ResultSet get() {
		ResultSet queryResult = null;
		
		try {
			
			DatabaseMetaData meta = connection.getMetaData();
			queryResult = meta.getTables(null, null, null, new String[] {"TABLE"});
			
		}
		catch(SQLException ex) {
			System.out.println("Error getting tables: "+ex);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return queryResult;
	}

}
