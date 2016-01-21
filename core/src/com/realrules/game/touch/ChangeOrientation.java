package com.realrules.game.touch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;

public class ChangeOrientation {
	
	private List<Observer> observers = new ArrayList<Observer>();
	private ArrayList<Orientation> validDirections;
	private Orientation orientation;
	private Random rand = new Random();
	private int xGameCoord;
	private int yGameCoord;
	
	public ChangeOrientation(int xGameCoord, int yGameCoord) 
	{
		this.xGameCoord = xGameCoord;
		this.yGameCoord = yGameCoord;
		setValidDirections(xGameCoord, yGameCoord);
	}
	
	public boolean changeOnTouch() {
		GameSprite sprite = WorldSystem.get().getMemberFromCoords(xGameCoord, yGameCoord);
		if(sprite != null && sprite.status != 0) {
			return false;
		}
		else {
			onChange();
			return true;
		}
	}
	
	public Orientation onChange() {
		//Change actor's orientation
		int choice = rand.nextInt(this.validDirections.size());
		orientation = this.validDirections.get(choice);
		return orientation;
	
	}
	
	public Orientation getOrientation() {
		return orientation;
	}


	public void setValidDirections(int xGameCoord, int yGameCoord) {
		
		validDirections = new ArrayList<Orientation> (Arrays.asList(Orientation.N, Orientation.E, Orientation.S, Orientation.W));
		
		if(xGameCoord == WorldSystem.get().getSystemWidth()-1 || WorldSystem.get().getMemberFromCoords(xGameCoord + 1, yGameCoord) == null) {
			validDirections.remove(Orientation.E);
		}
		if(xGameCoord == 0 || WorldSystem.get().getMemberFromCoords(xGameCoord - 1, yGameCoord) == null) {
			validDirections.remove(Orientation.W);
		}
		if(yGameCoord == WorldSystem.get().getSystemHeight()-1 || WorldSystem.get().getMemberFromCoords(xGameCoord, yGameCoord + 1) == null) {
			validDirections.remove(Orientation.S);
		}
		if(yGameCoord == 0 || WorldSystem.get().getMemberFromCoords(xGameCoord, yGameCoord - 1) == null) {
			validDirections.remove(Orientation.N);
		}

	}
}
