package com.realrules.game.state;

import com.realrules.game.main.Game.Head;

public class Follower {
	
	public FollowerType type = null;
	public int id = 0;
	
	public Follower(Head type, int id, String spritePath) {
		this.id = id;
		this.type = new FollowerType(spritePath, type);
	}

}
