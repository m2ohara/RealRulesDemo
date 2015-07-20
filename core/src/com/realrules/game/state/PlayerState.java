package com.realrules.game.state;

import java.util.ArrayList;
import java.util.List;

import com.realrules.game.demo.DemoGame.Head;

public class PlayerState {
	
	private ArrayList<FollowerType> followerTypes = null;
	private ArrayList<Follower> followers = null;
	private int level = 0;
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
		followerTypes.add(new FollowerType("sprites//gossiperFollowerPack.pack", Head.GOSSIPER));
		followerTypes.add(new FollowerType("sprites//promoterFollowerPack.pack", Head.INFLUENCER));
		followerTypes.add(new FollowerType("sprites//deceiverFollowerPack.pack", Head.DECEIVER));
		
		followers = new ArrayList<Follower>();
		followers.add(new Follower(Head.GOSSIPER, 1, "sprites//gossiperFollowerPack.pack"));
		followers.add(new Follower(Head.GOSSIPER, 2, "sprites//gossiperFollowerPack.pack"));
		followers.add(new Follower(Head.DECEIVER, 3, "sprites//deceiverFollowerPack.pack"));
		level = 1;
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
	
	public List<FollowerType> getFollowerTypes() {
		return followerTypes;
	}
	

}
