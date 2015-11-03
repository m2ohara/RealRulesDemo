package com.realrules.game.act;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;

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
	public void performActing(float delta, GameSprite actor, ArrayList<Orientation> validDirections) {
		
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
	
	private void setFrame(GameSprite actor, ArrayList<Orientation> validDirections) {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentDirection(validDirections);
			changeRotation(actor); //Temp
		}
		
	}
	
	private void updateCurrentDirection( ArrayList<Orientation> validDirections) {


		int choice = rand.nextInt(validDirections.size());
		if (validDirections.get(choice) == Orientation.N) {
			angle = 90;
			direction = 1;
		} 
		if (validDirections.get(choice) == Orientation.E) {
			angle = 0;
			direction = 1;
		}
		if (validDirections.get(choice) == Orientation.S) {
			angle = 90;
			direction = 0;
		}
		if (validDirections.get(choice) == Orientation.W) {
			angle = 0;
			direction = 0;
		}
	}
	
	private void changeRotation(GameSprite actor) {
		//Rotate this
		actor.setRotation((float) (angle));	
		actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(direction))));
	}
	
	private void performAutonomousInteraction(GameSprite actor) {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), WorldSystem.getCoordDirection(direction, angle));
			
		}
	}

	@Override
	public Orientation getCurrentCoordinate() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
