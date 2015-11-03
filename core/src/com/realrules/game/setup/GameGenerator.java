package com.realrules.game.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.realrules.game.main.Game.Head;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.state.FollowerType;
import com.realrules.game.state.PlayerState;

public class GameGenerator {
	
	public float skipPlacementProb;
	private Vector2 starterCoords;
	public Random rand;
	
	public GameGenerator() {
		rand = new Random();
		skipPlacementProb = ((float)(PlayerState.get().getLevel())/10);
	}
	
	public void populateFullCrowdScreen() {
		
		List<FollowerType> types = PlayerState.get().getFollowerTypes();
		Random crowdSetter = new Random();
		int starterX = crowdSetter.nextInt(WorldSystem.getSystemWidth()-1);
		starterCoords = new Vector2(starterX, WorldSystem.getSystemHeight()-1);
		for(int x = 0; x < WorldSystem.getSystemWidth(); x++) {
			for(int y = 0; y < WorldSystem.getSystemHeight(); y++) {
				GameSprite current = null;
				float rand = crowdSetter.nextFloat();
				if(rand < 0.33) {
					current = new GameSprite(Head.GOSSIPER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(0).directoryPath, true);
				}
				else if(rand >= 0.33 && rand < 0.66) {
					current = new GameSprite(Head.INFLUENCER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(1).directoryPath, true);
				}
				else {
					current = new GameSprite(Head.DECEIVER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(2).directoryPath, true);
				}
				if(y == WorldSystem.getSystemHeight()-1 && x == starterX) {
					current.status = 1; current.setColor(Color.ORANGE); 
				}
				GameProperties.get().addToActorGroup(current);
			}
		}
		
		populateLevelCrowdScreen();
	}
	
	public void populateLevelCrowdScreen() {
		
		Random directionStart = new Random();
		
		for(int x = 0; x < WorldSystem.getSystemWidth(); x++) {
			for(int y = 0; y < WorldSystem.getSystemHeight(); y++) {
				if(rand.nextFloat() < skipPlacementProb) {
					if(!(starterCoords.x == x && starterCoords.y == y)) {
						GameSprite sprite = WorldSystem.get().getMemberFromCoords(x, y);
						sprite.setVisible(false);
						if(isValidMemberRemoval()) {
							sprite.remove();
						}
						else {
							sprite.setVisible(true);
						}
					}
				}
			}
		}
		
	}
	
	ArrayList<GameMember> gameMembers = new ArrayList<GameMember>();
	public boolean isValidMemberRemoval() {
		
		boolean isValid = false;
		setGameMembers();
//		Set first sprite to current sprite, set as found & get neighbours
		GameMember startMember = getMemberByCoords(starterCoords);
		startMember.isFound = true;
//		Set neighbour tile to current, set first to current's parent tile
		ArrayList<GameMember> neighbours = getNeighbouringMembers(startMember);

		
		
		GameMember current = startMember;
		System.out.println("Start "+current.coords.x+" "+current.coords.y);
		int foundMembers = 1;
		boolean startingPlacement = true;
		int neighbourIdx = 0;

		while(current.isFirst == false || startingPlacement == true) {
			startingPlacement = false;
			if(current.isFound == false) {
//				Get neighbours, set current as found
				current.isFound = true;
//				System.out.println("Found "+current.coords.x+" "+current.coords.y);
				foundMembers++;
				neighbours = getNeighbouringMembers(current);
				neighbourIdx = 0;
			}
//			Else if current is found && neighbours are searched
			else if(current.isFound && neighbourIdx == neighbours.size()) {
//				Get parent & set parent to current
				if(current.coords.x == startMember.coords.x && current.coords.y == startMember.coords.y) {
					break;
				}
				current = current.parentMember;
//				System.out.println("Backtracking to "+current.coords.x+" "+current.coords.y);
				neighbourIdx = 0;
			}
			else {
//				Set next neighbour as current
				if(neighbours.get(neighbourIdx).isFound == false) {
					GameMember parent = current;
					current = neighbours.get(neighbourIdx);
//					System.out.println("Checking "+current.coords.x+" "+current.coords.y);
					current.parentMember = parent;
				}
				neighbourIdx++;
			}
		}
		
		isValid = foundMembers == gameMembers.size()-1 ? true : false;
		
		gameMembers.clear();
		
		System.out.println("Is valid "+isValid);
		return isValid;
	}
	
	private void setGameMembers() {
		for(int y = 0; y < WorldSystem.getSystemHeight(); y++) {
			for(int x = 0; x < WorldSystem.getSystemWidth(); x++) {
				if(WorldSystem.get().getMemberFromCoords(x, y) != null && WorldSystem.get().getMemberFromCoords(x, y).isVisible() == true) {
					GameMember member = new GameMember(new Vector2(x , y));
					if(starterCoords.x == x && starterCoords.y == y) {
						member.isFirst = true;
					}
					gameMembers.add(member);
				}
			}
		}
	}
	
	private ArrayList<GameMember> getNeighbouringMembers(GameMember member) {
		ArrayList<GameMember> neighbourMembers = new ArrayList<GameMember>();
		
		if(WorldSystem.get().getMemberFromCoords((int)member.coords.x, (int)member.coords.y + 1)!= null) {
			GameMember foundMember = getMemberByCoords(new Vector2(member.coords.x, member.coords.y + 1));
			if(foundMember != null) neighbourMembers.add(foundMember);
		}
		if(WorldSystem.get().getMemberFromCoords((int)member.coords.x + 1, (int)member.coords.y)!= null ) {
			GameMember foundMember = getMemberByCoords(new Vector2(member.coords.x+1, member.coords.y));
			if(foundMember != null) neighbourMembers.add(foundMember);
		}
		if(WorldSystem.get().getMemberFromCoords((int)member.coords.x, (int)member.coords.y - 1)!= null ) {
			GameMember foundMember = getMemberByCoords(new Vector2(member.coords.x, member.coords.y - 1));
			if(foundMember != null) neighbourMembers.add(foundMember);
		}
		if(WorldSystem.get().getMemberFromCoords((int)member.coords.x - 1, (int)member.coords.y)!= null ) {
			GameMember foundMember = getMemberByCoords(new Vector2(member.coords.x - 1, member.coords.y));
			if(foundMember != null) neighbourMembers.add(foundMember);
		}
		
		return neighbourMembers;
	}
	

	private GameMember getMemberByCoords(Vector2 coords) {
		for(GameMember member : gameMembers) {
			if(member.coords.x == coords.x && member.coords.y == coords.y) {
				return member;
			}
		}
		return null;
	}
	
	class GameMember { 
		public boolean isFound = false;
		public boolean isFirst = false;
		public Vector2 coords =  null;
		public GameMember parentMember = null;;
		
		public GameMember(Vector2 coords) {
			this.coords = coords;
		}
	}
	
	private int levelWinAmount;
	public int getLevelWinAmount() {
		return levelWinAmount;
	}

}
