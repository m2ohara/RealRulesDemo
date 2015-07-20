package com.realrules.game.state;

import com.realrules.game.demo.DemoGame.Head;

public class FollowerType {
	
	public Head head;
	public String spritePath;
	
	public FollowerType(String path, Head head) {
		this.head = head;
		this.spritePath = path;
	}
	

}
