package com.realrules.game.state;

import com.realrules.game.main.DemoGame.Head;

public class FollowerType {
	
	public Head head;
	public String directoryPath;
	
	public FollowerType(String path, Head head) {
		this.head = head;
		this.directoryPath = path;
	}
	

}
