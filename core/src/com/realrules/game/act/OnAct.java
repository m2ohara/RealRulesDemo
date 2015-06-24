package com.realrules.game.act;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;

public class OnAct implements IOnAct {
	
	private Random rand = new Random();
	private float stateLength = 4.0f * GameProperties.get().getUniversalTimeRatio();
	private float stateTime = stateLength;
	private float interactStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float interactStateTime = interactStateLength;
	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
	private float frameTime = frameLength;
	
	private Array<AtlasRegion> frames;
	
	private float interactP;
	private float rotateP;
	private int direction;
	private int angle;
	
	private int frameCount = 0;
	private TextureAtlas Up = new TextureAtlas(Gdx.files.internal("sprites//Donal//Up.pack"));
	private TextureAtlas Left = new TextureAtlas(Gdx.files.internal("sprites//Donal//Left.pack"));
	private TextureAtlas Right = new TextureAtlas(Gdx.files.internal("sprites//Donal//Right.pack"));
	private TextureAtlas Down = new TextureAtlas(Gdx.files.internal("sprites//Donal//Down.pack"));
	
	public OnAct(float rotateProbability, float interactProbability, String framesPath) 
	{
		rotateP = rotateProbability;
		interactP = interactProbability;
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		
//		Up = new TextureAtlas(Gdx.files.internal(framesPath+"//Up.pack"));
//		Left = new TextureAtlas(Gdx.files.internal(framesPath+"//Left.pack"));
//		Right = new TextureAtlas(Gdx.files.internal(framesPath+"//Right.pack"));
//		Down = new TextureAtlas(Gdx.files.internal(framesPath+"//Down.pack"));
		
//		updateCurrentAngle();
		changeSpriteOrientation();
		
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

		updateSprite(delta, actor);
	}
	
	private void changeSpriteOrientation() {
		
		if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.N) {
			frames = Up.getRegions();
		}
		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.E) {
			frames = Right.getRegions();
		}
		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.S) {
			frames = Down.getRegions();
		}
		else if(CoordinateSystem.getCoordDirection(direction, angle) == Coordinates.W) {
			frames = Left.getRegions();
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
	
	private void changeRotation(HeadSprite actor) {
		//Rotate this
		actor.setRotation((float) (angle));	
		actor.setDrawable(new TextureRegionDrawable(new TextureRegion(frames.get(direction))));
	}
	
	private void setFrame() {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			updateCurrentAngle();
			changeSpriteOrientation();
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
			actor.interaction.interact(actor, GameProperties.get().getActorGroup(), CoordinateSystem.getCoordDirection(direction, angle));
			
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
