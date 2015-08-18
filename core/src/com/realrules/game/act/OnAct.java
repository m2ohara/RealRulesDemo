package com.realrules.game.act;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.main.CoordinateSystem;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.CoordinateSystem.Coordinates;

public class OnAct implements IOnAct {
	
	private Random rand = new Random();
	private float stateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	private float stateTime = stateLength;
	private float interactStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float interactStateTime = interactStateLength;
	
	private Array<AtlasRegion> frames;
	
	private float interactP;
	private float rotateP;
	private int direction;
	private int angle;
	
	public OnAct(float rotateProbability, float interactProbability, String framesPath) 
	{
		rotateP = rotateProbability;
		interactP = interactProbability;
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		
	}

	@Override
	public void performActing(float delta, HeadSprite actor, ArrayList<Coordinates> validDirections) {
		
		if(stateTime >= stateLength) {
			stateTime = 0.0f;		
			setFrame(actor, validDirections);
		}
		
		else if( interactStateTime >= interactStateLength) {
			interactStateTime = 0.0f;
			performAutonomousInteraction(actor);
		}
		
		stateTime += delta;
		interactStateTime += delta;

	}
	
	private void setFrame(HeadSprite actor, ArrayList<Coordinates> validDirections) {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentDirection(validDirections);
			changeRotation(actor); //Temp
		}
		
	}
	
	private void updateCurrentDirection( ArrayList<Coordinates> validDirections) {


		int choice = rand.nextInt(validDirections.size());
		if (validDirections.get(choice) == Coordinates.N) {
			angle = 90;
			direction = 1;
		} 
		if (validDirections.get(choice) == Coordinates.E) {
			angle = 0;
			direction = 1;
		}
		if (validDirections.get(choice) == Coordinates.S) {
			angle = 90;
			direction = 0;
		}
		if (validDirections.get(choice) == Coordinates.W) {
			angle = 0;
			direction = 0;
		}
	}
	
	private void changeRotation(HeadSprite actor) {
		//Rotate this
		actor.setRotation((float) (angle));	
		actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(direction))));
	}
	
	private void performAutonomousInteraction(HeadSprite actor) {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), CoordinateSystem.getCoordDirection(direction, angle));
			
		}
	}

	@Override
	public int getCurrentDirection() {
		return direction;
	}

	@Override
	public int getCurrentAngle() {
		return angle;
	}
	
	//**************************** Talking heads logic
//	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
//	private float frameTime = frameLength;
//	private int frameCount = 0;
	
//	private void updateCurrentAngle( ArrayList<Coordinates> invalidDirections) {
//
//			//Set direction
//			direction = rand.nextInt(2);
//			//Set angle
//			int angleSpread = 90;
//			int angleSector = rand.nextInt(2);
//			int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
//			int startingAngle = angleSector == 0 ? 0 : 270;
//			angle = startingAngle + angleMultiple;
//	}
	
//	private void changeSpriteOrientation() {
//		
////		if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.N) {
////			frames = Up.getRegions();
////		}
////		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.E) {
////			frames = Right.getRegions();
////		}
////		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.S) {
////			frames = Down.getRegions();
////		}
////		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.W) {
////			frames = Left.getRegions();
////		}
//		
//	}
//	
//	private void updateSprite(float delta, HeadSprite actor) {
//		
//		if(frameTime >= frameLength) {
//			frameTime = 0.0f;
//			
//			if(frameCount > frames.size -1) {
//				frameCount = 0;
//			}
//			
//			actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(frameCount++))));
//			
//		}
//		
//		frameTime += delta;
//	}

}
