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
import com.realrules.game.main.Assets;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;

public class OnAnimateTalkingAct implements IOnAct{
	
	private Random rand = new Random();
	private float animateStateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	private float animateStateTime = animateStateLength;
	private float attemptInteractStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float attemptInteractStateTime = attemptInteractStateLength;
	
	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
	private float frameTime = frameLength;
	private int frameCount = 0;
	
	private GameSprite actor;
	private ArrayList<Orientation> validDirections;
	
	private HashMap<String, Array<AtlasRegion>> animationFrames = new HashMap<String, Array<AtlasRegion>>();
	private Array<AtlasRegion> frames;
	
	private float interactP;
	private float rotateP;
	private Orientation direction;
	
	public OnAnimateTalkingAct(float rotateProbability, float interactProbability, String framesPath, GameSprite actor, ArrayList<Orientation> validDirections) 
	{
		this.rotateP = rotateProbability;
		this.interactP = interactProbability;
		this.actor = actor;
		this.validDirections = validDirections;
		
		frames = new TextureAtlas(Gdx.files.internal(framesPath+"Default.pack")).getRegions();
		
		setFramePacks(framesPath);
		
		setFrame();
		
	}
	
	public OnAnimateTalkingAct(float rotateProbability, float interactProbability, GameSprite actor, ArrayList<Orientation> validDirections) 
	{
		this.rotateP = rotateProbability;
		this.interactP = interactProbability;
		this.actor = actor;
		this.validDirections = validDirections;
		
		frames = new TextureAtlas(Gdx.files.internal(actor.getFramesPath()+"Default.pack")).getRegions();
		
		setFramePacks(actor.getFramesPath());
		
		setFrame();
		
	}

	@Override
	public void performActing(float delta) {
		
		//Change actor's direction
		if(animateStateTime >= animateStateLength) {
			animateStateTime = 0.0f;		
			setFrame();
		}
		
		//Attempt interaction
		else if( attemptInteractStateTime >= attemptInteractStateLength) {
			attemptInteractStateTime = 0.0f;
			attemptAutonomousInteraction();
		}
		
		//Animate frames for current direction
		if(animateStateTime < animateStateLength) {
			updateSprite(delta, actor);
		}
		
		//If not interacting increment states
		if(!actor.isInteracting && !actor.isManualInteractor) {
			animateStateTime += delta;
			attemptInteractStateTime += delta;
		}
		//Otherwise continue interaction
		else {
			continueAutonomousInteraction();
		}
		
	}
	
	@Override
	public Orientation getCurrentCoordinate() {
		return this.direction;
	}
	
	private void setFrame() {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentDirection();
			changeSpriteOrientation();
		}
		
	}
	
	private void updateSprite(float delta, GameSprite actor) {
	
		if(frameTime >= frameLength) {
			frameTime = 0.0f;
			
			if(frameCount > frames.size -1) {
				frameCount = 0;
			}
			
			actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(frameCount++))));
			
		}
		
		frameTime += delta;
	}
	
	private void updateCurrentDirection() {
		int choice = rand.nextInt(this.validDirections.size());
		direction = this.validDirections.get(choice);
	}
	
	private void changeSpriteOrientation() {
	
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
	
	private void attemptAutonomousInteraction() {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), direction);
			
		}
	}
	
	private void continueAutonomousInteraction() {
		actor.interaction.interact(actor, GameProperties.get().getActorGroup(), direction);
	}
	
	private void setFramePacks(String framesPath) {
		Array<AtlasRegion> talkRight = Assets.get().getAssetManager().get(framesPath + "Right.pack", TextureAtlas.class).getRegions();
		
		animationFrames.put("TalkRight", talkRight);
		
		Array<AtlasRegion> talkLeft = Assets.get().getAssetManager().get(framesPath + "Left.pack", TextureAtlas.class).getRegions();
		
		animationFrames.put("TalkLeft", talkLeft);
		
		Array<AtlasRegion> talkAbove = Assets.get().getAssetManager().get(framesPath + "Above.pack", TextureAtlas.class).getRegions();
		
		animationFrames.put("TalkAbove", talkAbove);
		
		Array<AtlasRegion> talkBelow = Assets.get().getAssetManager().get(framesPath + "Below.pack", TextureAtlas.class).getRegions();
		
		animationFrames.put("TalkBelow", talkBelow);
	}

}
