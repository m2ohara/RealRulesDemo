package com.realrules.game.state;

import com.realrules.game.main.Game.Head;

public class FollowerType {
	
	public Head head;
	public String directoryPath;
	public int difficulty;
	
	public FollowerType(String path, Head head) {
		this.head = head;
		this.directoryPath = path;
	}
	
	public FollowerType(String path, Head head, int difficulty) {
		
	}
	

}
