package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public interface ISpriteBehaviour {
	
	//Touch behaviour
	void onTouch();
	
	//New act behaviour
	void onAct(float delta, GameSprite actor, ArrayList<Orientation> invalidDirections);
	
	int getInfluenceAmount();
	
	public Orientation getOrientation();
	
}
