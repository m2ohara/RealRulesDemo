package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public interface IHeadBehaviour {
	
	//Touch behaviour
	void onTouch();
	
	//New act behaviour
	void onAct(float delta, HeadSprite actor, ArrayList<Orientation> invalidDirections);
	
	int getInfluenceAmount();
	
	public Orientation getOrientation();
	
}
