 package com.realrules.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepo {
	
	private static PlayerRepo instance;
	private static Connection connection;
	
	private PlayerRepo() {
		connection = SqlConnection.get();
	}
	
	public static PlayerRepo instance() {
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
	
	public PlayerStateEntity get() {
		return new GetPlayerStateEntity().ExecuteTransaction().get(0);
	}
	
	public void update(PlayerStateEntity entity) {
		new UpdatePlayerStateEntity(entity);
	}
	
	public class GetPlayerStateEntity extends BaseExecuter<PlayerStateEntity> {

		@Override
		public void setSql() {
			this.sql = "SELECT * FROM PLAYER;";
			
		}

		@Override
		public List<PlayerStateEntity> getResult(ResultSet result) {
			List<PlayerStateEntity> resultList = new ArrayList<PlayerStateEntity>();
			try {
				while(result.next()) {
					
					resultList.add(new PlayerStateEntity(result.getInt("ID"), result.getInt("LEVEL"), result.getInt("LEVELUPTHRESHOLD"), result.getInt("REPUTATION"), result.getInt("MAXLEVEL")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resultList;
		}
		

	}
	
	public class UpdatePlayerStateEntity extends BaseExecuter<PlayerStateEntity> {
		
		private PlayerStateEntity entity = null;
		
		public UpdatePlayerStateEntity(PlayerStateEntity entity) {
			this.entity = entity;
		}

		@Override
		public void setSql() {
			this.sql = "INSERT INTO PLAYER STATE VALUES ("+entity.getId()+", "+entity.getLevel()+", "+entity.getLevelUpThreshold()+", "+entity.getReputation()+", "+entity.getMaxLevel()+";)";
			
		}

		@Override
		public List<PlayerStateEntity> getResult(ResultSet result) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
