package com.realrules.game.demo;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;

public class GossiperBehaviour implements IHeadBehaviour, ITouchActionBehaviour {
	
	HeadSprite gossiper;
	float stateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	float stateTime = stateLength;
	float InStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	float InStateTime = InStateLength;
	float TouchStateLength = 3.0f;
	float TouchStateTime = 0;
	private boolean isActive = false;
	private int direction = 0;
	private float rotateP = 0.8f;
	private TouchAction onTouch;
	private IOnAct onAct;
	private float interactSuccess = 0.2f;
	
	//Skills
	private int influenceAmount = 2;
	Random rand = new Random();
	
	
	public int getInfluenceAmount() {
		return influenceAmount;
	}

//	public GossiperBehaviour(HeadSprite gossiper, boolean isActive, IManualInteraction manInteraction) {
//		this.gossiper = gossiper;		
//		this.isActive = isActive;
//		onTouch = new GossiperTouchAction(manInteraction);
//		this.onTouch.setInteractorX(gossiper.getXGameCoord());
//		this.onTouch.setInteractorY(gossiper.getYGameCoord());
//		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, 0));
//		
//	}
	
	public GossiperBehaviour(HeadSprite gossiper, String framesPath, boolean isActive, IManualInteraction manInteraction) {
		this.gossiper = gossiper;		
		this.isActive = isActive;
		onTouch = new GossiperTouchAction(manInteraction);
		this.onTouch.setInteractorX(gossiper.getXGameCoord());
		this.onTouch.setInteractorY(gossiper.getYGameCoord());
		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, 0));
		
		onAct = new OnAct(rotateP, interactSuccess, framesPath);
	}

	@Override
	public void onAct(float delta) {
		
//		if(isActive) {
//			if(stateTime >= stateLength) {
//				stateTime = 0.0f;
//				setFrame();
//			}
//			stateTime += delta;
//			
//
//			if( InStateTime >= InStateLength) {
//				InStateTime = 0.0f;
//				performAutonomousInteraction();
//			}
//			InStateTime += delta;
//		}
	}

	@Override
	public void onDraw(Batch batch, float parentAlpha) {
	}

	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}		
		
	}
	
	//Implement frame setting
	private void setFrame() {
		//Based on rotation probability
		if(rand.nextFloat() < gossiper.rotateP) {
			//Set direction
			direction = rand.nextInt(2);
			gossiper.direction = direction;
			gossiper.currentFrame = gossiper.frames.get(direction);
			//Set angle
			int angleSpread = 90;
			int angleSector = rand.nextInt(2);
			int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
			int startingAngle = angleSector == 0 ? 0 : 270;
			int finalAngle = startingAngle + angleMultiple;
			
			//Rotate gossiper
			gossiper.setRotation((float) (finalAngle));	
			gossiper.setDrawable(new TextureRegionDrawable(new TextureRegion(gossiper.currentFrame)));
			
			//Rotate soundwave
			int soundWaveAngle = direction == 1 ? finalAngle : (finalAngle + 180) % 360;
			gossiper.soundWave.setRotation(soundWaveAngle);
			gossiper.soundWave.currentAngle = (int)gossiper.soundWave.getRotation();
			
			
			onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, finalAngle));
		}
		
	}
	
	private void performAutonomousInteraction() {
		Random rand = new Random();
		if(rand.nextFloat() < gossiper.interactSuccessP) {
			gossiper.soundWave.setVisible(true);
			gossiper.interaction.interactAutonomous(this.gossiper, GameProperties.get().getActorGroup());
			
		}
		else {
			gossiper.soundWave.setVisible(false);
		}
	}

	@Override
	public void onAct(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections) {
		// TODO Refactor acting into this method
//		this.onAct(delta);
		
		
		if(isActive) {
			onAct.performActing(delta, actor, invalidDirections);
			
			//Update direction  for touch action
			onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(onAct.getCurrentDirection(), onAct.getCurrentAngle()));
		}
		
	}

	@Override
	public void setInteractSprite(float x, float y) {
		// TODO Refactor soundwave creation into here
		
	}

	
	public int getDirection() {
		return onAct.getCurrentDirection();
	}

	@Override
	public int[] getValidInteractX() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getValidInteractY() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public float getRotateProbability() {
		return this.rotateP;
	}

	@Override
	public HeadSprite getInteractSprite() {
		return this.gossiper;
	}
	
	
}

