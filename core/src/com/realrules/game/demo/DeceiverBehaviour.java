package com.realrules.game.demo;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;

public class DeceiverBehaviour  implements IHeadBehaviour {
	
	Random rand = new Random();
	float stateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	float stateTime = stateLength;
	float InStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	float InStateTime = InStateLength;
	float TouchStateLength = 3.0f;
	float TouchStateTime = 0;
	private TouchAction onTouch;

	private Array<AtlasRegion> frames;
	private TextureRegion currentFrame;
	private float rotateP = 0.8f;
	private float interactSuccessP = 0.4f;
	//Current angles
	int deceiverAngle = 0;
	int soundWaveAngle = 0;
	private InteractSprite soundWave;
	
	//Members
	public int status = 0; //0 : neutral, 1 : for 2 : against
	private int influenceAmount = 3;
	public float argueSuccessP = 0.2f;
	private boolean isActive = true;
	
	private int direction; //0 : right, 1 : left

	
	public DeceiverBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		this.direction = 0;
		this.isActive = isActive;
		
		onTouch = new DeceiverTouchAction(manInteraction);
		this.onTouch.setInteractorX(x);
		this.onTouch.setInteractorY(y);
		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, 0));
		

	}
	
	public void setInteractSprite(float x, float y) {
		//Set interact sprite
		this.soundWave = new InteractSprite(x, y, "sprites//soundWaveFollower.pack"); 
		this.soundWave.setTouchable(Touchable.disabled);
		GameProperties.get().addToSoundWaveGroup(this.soundWave);
	}

	@Override
	public void onAct(float delta) {
		//Not implemented
		
	}

	@Override
	public void onDraw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}

	@Override
	public void onAct(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections) {
		
		if(isActive) {
			if(stateTime >= stateLength) {
				stateTime = 0.0f;
				updateCurrentAngle();
			}

			else if( InStateTime >= InStateLength) {
				InStateTime = 0.0f;
				performAutonomousInteraction(actor);
			}
			
			stateTime += delta;
			InStateTime += delta;
		}
		

		
		//Rotate this
		actor.setRotation((float) (deceiverAngle));	
		actor.setDrawable(new TextureRegionDrawable(new TextureRegion(this.currentFrame)));
		

		this.soundWave.setRotation(soundWaveAngle);
		this.soundWave.currentAngle = (int)this.soundWave.getRotation();
		
	}
	
	//Deceiver behaviours
	private void updateCurrentAngle() {
		//Based on rotation probability
		if(rand.nextFloat() < this.rotateP) {
			
			//Set direction
			direction = rand.nextInt(2);
			this.currentFrame = this.frames.get(direction);
			//Set angle
			int angleSpread = 90;
			int angleSector = rand.nextInt(2);
			int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
			int startingAngle = angleSector == 0 ? 0 : 270;
			deceiverAngle = startingAngle + angleMultiple;
			
			//Rotate soundwave
			soundWaveAngle = direction == 1 ? deceiverAngle : (deceiverAngle + 180) % 360;
			
			onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, deceiverAngle));
			
		}
	}
	
	private void performAutonomousInteraction(HeadSprite actor) {
		Random rand = new Random();
		if(rand.nextFloat() < this.interactSuccessP) {
			this.soundWave.setVisible(true);
			actor.interaction.interactAutonomous(actor, GameProperties.get().getActorGroup());
			
		}
		else {
			this.soundWave.setVisible(false);
		}
	}

	
	public int getDirection() {
		return this.direction;
	}

	@Override
	public float getRotateProbability() {
		return this.rotateP;
	}

	@Override
	public HeadSprite getInteractSprite() {
		// TODO Auto-generated method stub
		return null;
	}
}
