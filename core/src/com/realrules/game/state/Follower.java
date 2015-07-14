package com.realrules.game.state;

import com.realrules.game.demo.DemoGame.Head;

public class Follower {
	
	Head type = null;
	int id = 0;
	String spritePath = "";
	
	public Follower(Head type, int id, String spritePath) {
		this.type = type;
		this.id = id;
		this.spritePath = spritePath;
	}

}
