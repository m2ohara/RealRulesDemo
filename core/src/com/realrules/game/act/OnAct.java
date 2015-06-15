package com.realrules.game.act;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;

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
		
		updateCurrentAngle();
		
	}

	@Override
	public void performActing(float delta, HeadSprite actor) {
		
		if(stateTime >= stateLength) {
			stateTime = 0.0f;
			setFrame();
		}
		
		else if( interactStateTime >= interactStateLength) {
			interactStateTime = 0.0f;
			performAutonomousInteraction(actor);
		}
		
		stateTime += delta;
		interactStateTime += delta;
		
		//Rotate this
		actor.setRotation((float) (angle));	
		actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(direction))));
		
	}
	
	private void setFrame() {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentAngle();
		}
		
	}
	
	private void updateCurrentAngle() {

			//Set direction
			direction = rand.nextInt(2);
			//Set angle
			int angleSpread = 90;
			int angleSector = rand.nextInt(2);
			int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
			int startingAngle = angleSector == 0 ? 0 : 270;
			angle = startingAngle + angleMultiple;
	}
	
	private void performAutonomousInteraction(HeadSprite actor) {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
//			this.soundWave.setVisible(true);
			actor.interaction.interactAutonomous(actor, GameProperties.get().getActorGroup());
			
		}
		else {
//			this.soundWave.setVisible(false);
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

}
