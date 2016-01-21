package com.realrules.game.act;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.main.Assets;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.ChangeOrientation;

public class OnAnimateTalkingAct implements IOnAct{
	
	private float animateStateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	private float animateStateTime = animateStateLength;
	private float attemptInteractStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float attemptInteractStateTime = attemptInteractStateLength;
	
	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
	private float frameTime = frameLength;
	private int frameCount = 0;
	
	private HashMap<String, Array<AtlasRegion>> animationFrames = new HashMap<String, Array<AtlasRegion>>();
	private Array<AtlasRegion> frames;
	
	private GameSprite actor;
	
	//Orientation properties
	private ChangeOrientation onRandom;
//	private Orientation orientation;
	
//	private ArrayList<Orientation> validDirections;
	private float interactP;
	private float rotateP;
	private Random rand = new Random();
	
//	public OnAnimateTalkingAct(float rotateProbability, float interactProbability, String framesPath, GameSprite actor) 
//	{
//		
//		this.rotateP = rotateProbability;
//		this.interactP = interactProbability;
//		this.actor = actor;
////		this.validDirections = validDirections;
//		
//		frames = new TextureAtlas(Gdx.files.internal(framesPath+"Default.pack")).getRegions();
//		
//		setFramePacks(framesPath);
//		
//		setFrame();
//		
//	}
	
	public OnAnimateTalkingAct(float rotateProbability, float interactProbability, GameSprite actor, ChangeOrientation onRandom) 
	{

		this.rotateP = rotateProbability;
		this.interactP = interactProbability;
		this.actor = actor;
		this.onRandom = onRandom;
		
		frames = new TextureAtlas(Gdx.files.internal(actor.getFramesPath()+"Default.pack")).getRegions();
		
		setFramePacks(actor.getFramesPath());
		
		changeSpriteOrientation();
		
	}

	@Override
	public void performActing(float delta) {
		
		//Change actor's orientation
		if(animateStateTime >= animateStateLength) {
			animateStateTime = 0.0f;		
			setFrame();
		}
		
		//Animate frames for current direction
		if(animateStateTime < animateStateLength) {
			updateSprite(delta, actor);
		}
		
		//Attempt interaction
		if( attemptInteractStateTime >= attemptInteractStateLength && GameProperties.get().IsSwipeInteraction == true) {
//			System.out.println("Actor "+actor.status+" attempting to interact");
			attemptInteractStateTime = 0.0f;
			attemptAutonomousInteraction();
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
	private void attemptAutonomousInteraction() {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactP) {
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), onRandom.getOrientation());
			
		}
	}
	
	private void continueAutonomousInteraction() {
		actor.interaction.interact(actor, GameProperties.get().getActorGroup(), onRandom.getOrientation());
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
	
	public void setFrame() {
		//Based on rotation probability
		if(actor.status > 1 && rand.nextFloat() < this.rotateP) {
			changeSpriteOrientation();
		}
		
	}
	
	@Override
	public void changeSpriteOrientation() {
		
		this.onRandom.onChange();
		
		if(onRandom.getOrientation() == Orientation.N) {
			frames = animationFrames.get("TalkAbove");
		}
		else if(onRandom.getOrientation() == Orientation.E) {
			frames = animationFrames.get("TalkRight");
		}
		else if(onRandom.getOrientation() == Orientation.W) {
			frames = animationFrames.get("TalkLeft");
		}
		else if(onRandom.getOrientation() == Orientation.S) {
			frames = animationFrames.get("TalkBelow");
		}
	}

}
