package com.realrules.game.state;

import java.util.ArrayList;
import java.util.List;

import com.realrules.game.main.DemoGame.Head;

public class PlayerState {
	
	private ArrayList<FollowerType> followerTypes = null;
	private ArrayList<Follower> followers = null;
	private int level = 0;
	private int reputation = 0;
	private static PlayerState instance;
	
	public static PlayerState get() {
		
		if(instance == null) {
			instance = new PlayerState();
		}
		return instance;
	}
	
	private PlayerState() {
	}
	
	public void generateDummyProperties() {
		
		followerTypes = new ArrayList<FollowerType>();
		followerTypes.add(new FollowerType("sprites//Meep//Gossiper//", Head.GOSSIPER));
		followerTypes.add(new FollowerType("sprites//Meep//Promoter//", Head.INFLUENCER));
		followerTypes.add(new FollowerType("sprites//Meep//Deceiver//", Head.DECEIVER));
		
		followers = new ArrayList<Follower>();
		followers.add(new Follower(Head.GOSSIPER, 1, "sprites//Meep//Gossiper//Default.pack"));
		followers.add(new Follower(Head.INFLUENCER, 2, "sprites//Meep//Promoter//Default.pack"));
		followers.add(new Follower(Head.DECEIVER, 3, "sprites//Meep//Deceiver//Default.pack"));
		level = 1;
		reputation = 1000;
	}
	
	//TODO: Implement Load player data
	public void load() {
		//Get followers
		
		//Get level
		
		//Hardcoded
		generateDummyProperties();
	}
	
	public int getLevel() {
		return level;
	}
	
	public List<Follower> getFollowers() {
		return followers;
	}
	
	public void addFollowers(List<Follower> followersToAdd) {
		followers.addAll(followersToAdd);
	}
	
	public List<FollowerType> getFollowerTypes() {
		return followerTypes;
	}
	
	public void setReputationPoints(int points) {
		reputation = points;
	}
	
	public int getReputationPoints() {
		return reputation;
	}
	

}
