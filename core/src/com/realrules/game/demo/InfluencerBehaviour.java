package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;

public class InfluencerBehaviour implements IHeadBehaviour {
	
	private Random rand = new Random();
	private float stateLength = 2.0f * GameProperties.get().getUniversalTimeRatio();
	private float stateTime = stateLength;
	private float InStateLength = 0.7f * GameProperties.get().getUniversalTimeRatio();
	private float InStateTime = InStateLength;
	private float TouchStateLength = 3.0f;
	private float TouchStateTime = 0;
	
	public InfluencerBehaviour() {
		
	}

	@Override
	public void onAct(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAct(float delta, HeadSprite actor) {
		// TODO Auto-generated method stub
		
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
