package com.realrules.game.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;

public class OnAnimateTalkingAct implements IOnAct{
	
	private Random rand = new Random();
	private float stateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	private float stateTime = stateLength;
	private float interactStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float interactStateTime = interactStateLength;
	
	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
	private float frameTime = frameLength;
	private int frameCount = 0;
	
	private HashMap<String, Array<AtlasRegion>> animationFrames = new HashMap<String, Array<AtlasRegion>>();
	private Array<AtlasRegion> frames;
	
	private float interactP;
	private float rotateP;
	private Orientation direction;
	
	public OnAnimateTalkingAct(float rotateProbability, float interactProbability, String framesPath) 
	{
		rotateP = rotateProbability;
		interactP = interactProbability;
		
		frames = new TextureAtlas(Gdx.files.internal(framesPath+"Default.pack")).getRegions();
		
		setFramePacks(framesPath);
		
	}

	@Override
	public void performActing(float delta, HeadSprite actor, ArrayList<Orientation> validDirections) {
		
		if(stateTime >= stateLength) {
			stateTime = 0.0f;		
			setFrame(actor, validDirections);
		}
		
		else if( interactStateTime >= interactStateLength) {
			interactStateTime = 0.0f;
			performAutonomousInteraction(actor);
		}
		
		if(stateTime < stateLength) {
			updateSprite(delta, actor);
		}
		
		stateTime += delta;
		interactStateTime += delta;
		
	}
	
	@Override
	public Orientation getCurrentCoordinate() {
		return this.direction;
	}
	
	private void setFrame(HeadSprite actor, ArrayList<Orientation> validDirections) {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentDirection(validDirections);
			changeSpriteOrientation(actor);
		}
		
	}
	
	private void updateSprite(float delta, HeadSprite actor) {
	
		if(frameTime >= frameLength) {
			frameTime = 0.0f;
			
			if(frameCount > frames.size -1) {
				frameCount = 0;
			}
			
			actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(frameCount++))));
			
		}
		
		frameTime += delta;
	}
	
	private void updateCurrentDirection( ArrayList<Orientation> validDirections) {
		int choice = rand.nextInt(validDirections.size());
		direction = validDirections.get(choice);
	}
	
	private void changeSpriteOrientation(HeadSprite actor) {
	
		if(direction == Orientation.N) {
			frames = animationFrames.get("TalkAbove");
		}
		else if(direction == Orientation.E) {
			frames = animationFrames.get("TalkRight");
		}
		else if(direction == Orientation.W) {
			frames = animationFrames.get("TalkLeft");
		}
		else if(direction == Orientation.S) {
			frames = animationFrames.get("TalkBelow");
		}
	}
	
	private void performAutonomousInteraction(HeadSprite actor) {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), direction);
			
		}
	}
	
	private void setFramePacks(String framesPath) {
		Array<AtlasRegion> talkRight = new TextureAtlas(Gdx.files.internal(framesPath + "Right.pack")).getRegions();
		
		animationFrames.put("TalkRight", talkRight);
		
		Array<AtlasRegion> talkLeft = new TextureAtlas(Gdx.files.internal(framesPath + "Left.pack")).getRegions();
		
		animationFrames.put("TalkLeft", talkLeft);
		
		Array<AtlasRegion> talkAbove = new TextureAtlas(Gdx.files.internal(framesPath + "Above.pack")).getRegions();
		
		animationFrames.put("TalkAbove", talkAbove);
		
		Array<AtlasRegion> talkBelow = new TextureAtlas(Gdx.files.internal(framesPath + "Below.pack")).getRegions();
		
		animationFrames.put("TalkBelow", talkBelow);
	}

}