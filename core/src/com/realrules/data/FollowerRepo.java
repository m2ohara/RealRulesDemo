package com.realrules.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public static FollowerRepo instance() {
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
	
	public List<Follower> getFollowers() {
		return new GetFollowers().ExecuteTransaction();
	}
	
	public void createFollower(Follower entity) {
		new CreateFollower(entity).ExecuteTransaction();
	}
	
	public void updateFollower(Follower entity) {
		new UpdateFollower(entity).ExecuteTransaction();
	}
	
	public class GetFollowers extends BaseExecuter<Follower>{

		@Override
		public void setSql() {
			this.sql = "SELECT f.rowid, ft.ID, ft.IMAGE_PATH FROM FOLLOWER f JOIN FOLLOWERTYPE ft ON f.TYPE_ID = ft.ID;";
		}

		@Override
		public List<Follower> getResult(ResultSet result) {
			List<Follower> resultList = new ArrayList<Follower>();	
			
			try {
				while(result.next()) {
					resultList.add(new Follower(result.getInt("rowid"), new FollowerType(result.getInt("ID"), result.getString("IMAGE_PATH"))));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resultList;
		}
	}
	
	public class CreateFollower extends BaseExecuter<Follower> {
		
		private Follower entity;
		
		public CreateFollower(Follower entity) {
			this.entity = entity;
		}

		@Override
		public void setSql() {
			this.sql = "INSERT INTO FOLLOWER VALUES ("+entity.type.getId()+");";
			
		}

		@Override
		public List<Follower> getResult(ResultSet result) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public class UpdateFollower extends BaseExecuter<Follower> {
		
		private Follower entity;
		
		public UpdateFollower(Follower entity) {
			this.entity = entity;
		}

		@Override
		public void setSql() {
			this.sql = "UPDATE FOLLOWER SET TYPE_ID = "+entity.getFollowerType().getId()+" WHERE rowid = "+entity.getId()+";";
			
		}

		@Override
		public List<Follower> getResult(ResultSet result) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	
//	public ArrayList<Follower> getFollowers() {
//	
//	Statement getFollowersStatement = null;
//	
//	//Initialise
//	ResultSet result = null;
//	ArrayList<Follower> followers = new ArrayList<Follower>();	
//	String SQL = "SELECT ft.ID, ft.IMAGE_PATH FROM FOLLOWER f JOIN FOLLOWERTYPE ft ON f.TYPE_ID = ft.ID;";
//	
//	
//	try {
//		
//		//Create user
//		getFollowersStatement = connection.createStatement();
//		result = getFollowersStatement.executeQuery(SQL);
//		
//		while(result.next()) {
//			int id = result.getInt("ID");
//			String imagePath = result.getString("IMAGE_PATH");
//			followers.add(new Follower(Head.GOSSIPER, id, imagePath));
//		}
//	}
//	catch(SQLException ex) {
//		//TO DO: Log error
//		System.out.println("Error: "+ex);
//	}
//	finally {
//		try {
//			getFollowersStatement.close();
//			result.close();
//			connection.close();
//		}
//		catch(SQLException e) {
//			//TO DO: Log error
//			System.out.println("Error closing statement: "+e);
//		}
//	}
//	
//	
//	return followers;
//}
//	public ArrayList<FollowerType> getFollowerTypes() {
//		
//		Statement getFollowersStatement = null;
//		
//		//Initialise
//		ResultSet result = null;
//		ArrayList<FollowerType> followerTypes = new ArrayList<FollowerType>();	
//		String SQL = "SELECT * FROM FOLLOWERTYPE;";
//		
//		
//		try {
//			
//			//Create user
//			getFollowersStatement = connection.createStatement();
//			result = getFollowersStatement.executeQuery(SQL);
//			
//			while(result.next()) {
//				String imagePath = result.getString("IMAGE_PATH");
//				followerTypes.add(new FollowerType(imagePath, Head.GOSSIPER));
//			}
//		}
//		catch(SQLException ex) {
//			//TO DO: Log error
//			System.out.println("Error: "+ex);
//		}
//		finally {
//			try {
//				getFollowersStatement.close();
//				result.close();
//				connection.close();
//			}
//			catch(SQLException e) {
//				//TO DO: Log error
//				System.out.println("Error closing statement: "+e);
//			}
//		}
//		
//		
//		return followerTypes;
//	}

}
