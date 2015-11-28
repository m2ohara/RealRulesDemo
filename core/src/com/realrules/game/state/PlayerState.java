package com.realrules.game.state;

import java.util.ArrayList;
import java.util.List;

import com.realrules.data.FollowerRepo;
import com.realrules.game.main.Game.Head;

public class PlayerState {
	
	private ArrayList<FollowerType> followerTypes = new ArrayList<FollowerType>();
	private ArrayList<Follower> followers = new ArrayList<Follower>();
	private int level;
	private int maxLevel = 5;
	private int reputation = 0;
	private int levelUpLimit;
	private static PlayerState instance;
	
	public static PlayerState get() {
		
		if(instance == null) {
			instance = new PlayerState();
		}
		return instance;
	}
	
	private PlayerState() {
		
//		FollowerRepo.getInstance().createDB();
		FollowerRepo.get().getFollowers();
		
	}
	
	public void generateDummyProperties() {
		
		followerTypes.add(new FollowerType("sprites//Meep//Gossiper//", Head.GOSSIPER));
		followerTypes.add(new FollowerType("sprites//Meep//Promoter//", Head.INFLUENCER));
		followerTypes.add(new FollowerType("sprites//Meep//Deceiver//", Head.DECEIVER));
		
		followers.add(new Follower(Head.GOSSIPER, 1, "sprites//Meep//Gossiper//Default.pack"));
		followers.add(new Follower(Head.INFLUENCER, 2, "sprites//Meep//Promoter//Default.pack"));
		followers.add(new Follower(Head.DECEIVER, 3, "sprites//Meep//Deceiver//Default.pack"));
		level = 0;
		reputation = 1000;
		levelUpLimit = 1000;
		maxLevel = 5;
	}
	
	//TODO: Implement Load player data
	public void load() {
		
		//Get followers
		followers = FollowerRepo.get().getFollowers();
		followerTypes = FollowerRepo.get().getFollowerTypes();
		
		//Get level
		
		//Hardcoded
		generateDummyProperties();
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getMaxLevel() {
		return maxLevel;
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
	
	public int getLevelUpLimit() {
		return levelUpLimit;
	}
	

}
