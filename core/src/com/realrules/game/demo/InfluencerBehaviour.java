package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;

public class InfluencerBehaviour implements IHeadBehaviour {
	
	private Random rand = new Random();
	
	private float touchStateLength = 3.0f;
	private float touchStateTime = 0;
	
	private int interactSpriteAngle = 0;
	private int direction; //0 : right, 1 : left

	
	//Members
	public int status = 0; //0 : neutral, 1 : for 2 : against
	public float argueSuccessP = 0.2f;
	private float rotateP = 0.8f;
	private int influenceAmount = 3;
	private float interactSuccessP = 0.4f;
	private boolean isActive = true;
	private TouchAction onTouch;
	private IOnAct onAct;
	
	
	public InfluencerBehaviour(boolean isActive, String framesPath, int x, int y) {
		this.direction = 0;
		this.isActive = isActive;
		
		onTouch = new DeceiverTouchAction();
		onAct = new OnAct(rotateP, framesPath);
		
	}

	@Override
	public void onAct(float delta) {
		// TODO Refactor out
		
	}

	@Override
	public void onDraw(Batch batch, float parentAlpha) {
		// TODO Refactor out
		
	}

	@Override
	public void onTouch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAct(float delta, HeadSprite actor) {
		
		if(isActive) {
			onAct.performActing(delta, actor);
		
			//Rotate interact sprite
			interactSpriteAngle = onAct.getCurrentDirection() == 1 ? onAct.getCurrentAngle() : (onAct.getCurrentAngle() + 180) % 360;
			
			//Update direction  for touch action
			onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(onAct.getCurrentDirection(), onAct.getCurrentAngle()));
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setInteractSprite(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HeadSprite getInteractSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotateProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

}
