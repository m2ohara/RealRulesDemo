package com.realrules.game.state;

import java.util.ArrayList;
import java.util.List;

import com.realrules.game.demo.DemoGame.Head;

public class PlayerState {
	
	private ArrayList<Head> followerTypes = null;
	private ArrayList<Follower> followers = null;
	private int level = 0;
	
	public PlayerState() {
	}
	
	public void generateDummyProperties() {
		
		followerTypes = new ArrayList<Head>();
		followerTypes.add(Head.GOSSIPER);
		followerTypes.add(Head.GOSSIPER);
		followerTypes.add(Head.DECEIVER);
		
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
	
	public int getAmountOfFollowerType(Head type) {
		int count = 0;
		
		for(Follower follower : followers) {
			if(follower.type == type) {
				count++;
			}
		}
		return count;
	}
	
	public List<Head> getFollowerTypes() {
		return followerTypes;
	}
	

}
