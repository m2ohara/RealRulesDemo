package com.realrules.data;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerRepo {
	
	private static PlayerRepo instance;
	private static Connection connection;
	
	private PlayerRepo() {
		connection = SqlConnection.get();
	}
	
	public static PlayerRepo get() {
		if(instance == null) {
			instance = new PlayerRepo();
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

}
