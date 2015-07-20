package com.realrules.game.state;

import com.realrules.game.demo.DemoGame.Head;

public class Follower {
	
	public FollowerType type = null;
	public int id = 0;
//	public String spritePath = "";
	
	public Follower(Head type, int id, String spritePath) {
		this.id = id;
//		this.spritePath = spritePath;
		this.type = new FollowerType(spritePath, type);
	}

}
