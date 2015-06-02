package com.realrules.game.demo;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IHeadBehaviour {
	
	//Act behaviour (redundant)
	void onAct(float delta);
	
	//Draw behaviour
	void onDraw(Batch batch, float parentAlpha);
	
	//Touch behaviour
	void onTouch();
	
	//New act behaviour
	void onAct(float delta, HeadSprite actor);
	
	int getInfluenceAmount();
	
	void setInteractSprite(float x, float y);
	
	HeadSprite getInteractSprite();
	
	int getDirection(); //Refactor out
	
	float getRotateProbability();
	
}
