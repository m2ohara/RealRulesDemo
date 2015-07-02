package com.realrules.game.demo;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class CoordinateSystem {
	
	private static int systemWidth = 3;
	private static int systemHeight = 5;
	public static enum Coordinates {N, E, S, W}
	private static CoordinateSystem instance;
	//Properties related to setting sprites on game screen
	public final int xGrid = systemWidth;
	public final int yGrid = systemHeight;
	public static float headSpriteH = 72;
	public static int headSpriteW = 72;
	private float hudYCoord;
	
	private ArrayList<Float> gameXCoords = new ArrayList<Float>();
	public static ArrayList<Float>  gameYCoords = new ArrayList<Float>();
	
	private ArrayList<Float> hudXCoords = new ArrayList<Float>();
	private ArrayList<Float> hudYCoords = new ArrayList<Float>();
	
	public static CoordinateSystem get() {
		if(instance == null) {
			instance = new CoordinateSystem();
		}
		return instance;
	}
	
	private CoordinateSystem() {
		setGameCoords();
		
		setHudCoords();
	}
	
	private void setGameCoords() {
		float centreX = (Gdx.graphics.getWidth()) / 2; 
		float xSpan = headSpriteW*xGrid;
		float startX = centreX - (xSpan/2);
		float spanLengthX = startX + xSpan;
		for(float x = startX; x < spanLengthX; x+=headSpriteW) {
			getGameXCoords().add(x);
		}
		
		float centreY = (Gdx.graphics.getHeight()) / 2; 
		float ySpan = headSpriteH*yGrid;
		float startY = centreY + (ySpan/2);
		float spanLengthY = startY - ySpan;
		for(float y = startY; y > spanLengthY; y-=headSpriteH) {
			gameYCoords.add(y);
		}
	}
	
	public ArrayList<Float> getGameXCoords() {
		return gameXCoords;
	}
	
	public ArrayList<Float> getGameYCoords() {
		return gameYCoords;
	}
	
	private void setHudCoords() {
		
		setYHudCoord();
		//Set coordinates for followers in HUD
		int spriteTypeCount = GameProperties.get().getSpritePaths().size();	
		
		float centreX = (Gdx.graphics.getWidth()) / 2; 
		float xSpan = headSpriteW*spriteTypeCount;
		float startX = centreX - (xSpan/2);
		float spanLengthX = startX + xSpan;
		for(float x = startX; x < spanLengthX; x+=headSpriteW) {
			hudXCoords.add(x);
			hudYCoords.add(hudYCoord); 
		}
		
		
	}
	
	private void setYHudCoord() {
		//Start from centre
		float centreY = (Gdx.graphics.getHeight()) / 2; 
		float ySpan = headSpriteH*yGrid;
		float startY = centreY + (ySpan/2);
		float spanLengthY = startY - ySpan;
		hudYCoord = (float) (spanLengthY - (headSpriteH*0.5));
	}
	
	public ArrayList<Float> getHudXCoords() {
		return hudXCoords;
	}
	
	public ArrayList<Float> getHudYCoords() {
		return hudYCoords;
	}


	public void setDirection(HeadSprite actor) {
		//TODO: Implement
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
	
	public HeadSprite getMemberFromCoords(int gameXPos, int gameYPos) {
		
		if(GameProperties.get().getActorGroup() != null) {
			if(gameXPos != -1 && gameXPos < systemWidth && gameYPos != -1 && gameYPos < systemHeight){
				Array<Actor> actors = GameProperties.get().getActorGroup().getChildren();
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
		if(coordinate >= 0 &&  coordinate < systemHeight)
		{	
			return true;
		}
		return false;
	}
		
	
	public static boolean isValidXCoordinate(int coordinate) {
		if(coordinate >= 0 && coordinate < systemWidth)
		{	
			return true;
		}
		return false;
	}
	
	protected void setPositionFromCentre(Actor actorToSet, float _xCentreOffset, float _yCentreOffset) {
		
		//Centre actor
		float x = (Gdx.graphics.getWidth() - actorToSet.getWidth()) /2 ;
		float y = (Gdx.graphics.getHeight()  - actorToSet.getHeight()) / 2;
		
		x += _xCentreOffset;
		y += _yCentreOffset;
		
		actorToSet.setPosition(x, y);
		
		_xCentreOffset = actorToSet.getX();
		_yCentreOffset = actorToSet.getY();
	}

}
