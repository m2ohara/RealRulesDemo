package com.realrules.game.demo;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.DemoGame.HeadSprite;

public class CoordinateSystem {
	
	private static int systemWidth = 3;
	private static int systemHeight = 4;
	public static enum Coordinates {N, E, S, W}
	private static CoordinateSystem instance;
	//Properties related to setting sprites on game screen
	public static ArrayList<Float> gameXCoords = new ArrayList<Float>();
	public static ArrayList<Float>  gameYCoords = new ArrayList<Float>();
	public final int xGrid = 3;
	public final int yGrid = 4;
	float headSpriteH = 72;
	int headSpriteW = 72;
	
	public static CoordinateSystem get() {
		if(instance == null) {
			return new CoordinateSystem();
		}
		return instance;
	}
	
	private CoordinateSystem() {
		setGameCoords();
	}
	
	private void setGameCoords() {
		float centreX = (Gdx.graphics.getWidth()) / 2; 
		float xSpan = headSpriteW*xGrid;
		float startX = centreX - (xSpan/2);
		float spanLengthX = startX + xSpan;
		for(float x = startX; x < spanLengthX; x+=headSpriteW) {
			gameXCoords.add(x);
		}
		
		float centreY = (Gdx.graphics.getHeight()) / 2; 
		float ySpan = headSpriteH*yGrid;
		float startY = centreY + (ySpan/2);
		float spanLengthY = startY - ySpan;
		for(float y = startY; y > spanLengthY; y-=headSpriteH) {
			gameYCoords.add(y);
		}
	}
	
	public void setDirection(HeadSprite actor) {

	}
	
	public static Coordinates getCoordDirection(int direction, int angle ) {
		
		if(direction == 1) { //Right
			if(angle == 0) {
				return Coordinates.E;
			}
			else if(angle == 90) {
				return Coordinates.N;
			}
			else if(angle == 270) {
				return Coordinates.S;
			}
			
		}
		else if(direction == 0) { //Left
			if(angle == 0) {
				return Coordinates.W;
			}
			else if(angle == 90) {
				return Coordinates.S;
			}
			else if(angle == 270) {
				return Coordinates.N;
			}
		}
		return null;
	}
	
	public HeadSprite getMemberFromCoords(int gameXPos, int gameYPos, Group group) {
		
		if(group != null) {
			if(gameXPos != -1 && gameXPos < systemWidth && gameYPos != -1 && gameYPos < systemHeight){
				Array<Actor> actors = group.getChildren();
				for(Actor actor : actors) {
						HeadSprite headSprite = (HeadSprite)actor;
						if(gameXCoords.indexOf(headSprite.getStartingX()) == gameXPos && gameYCoords.indexOf(headSprite.getStartingY()) == gameYPos)
							return headSprite;
				}
			}
		}
		return null;
	}
	
	public static int getSystemWidth() {
		return systemWidth;
	}

	public static int getSystemHeight() {
		return systemHeight;
	}
	
	//Determine direction object is facing
	public static Coordinates getCoordinates(int dir, int rotation) {
		
		//TODO: dev logic for getting coordinates
		if(dir == 0){

		}
		return null;
			
		
	}
	
	public static boolean isValidYCoordinate(int coordinate) {
		if(coordinate > 0 &&  coordinate <= systemHeight)
		{	
			return true;
		}
		return false;
	}
		
	
	public static boolean isValidXCoordinate(int coordinate) {
		if(coordinate > 0 && coordinate <= systemWidth)
		{	
			return true;
		}
		return false;
	}

}
