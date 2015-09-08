package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.CoordinateSystem.Coordinates;

public interface IHeadBehaviour {
	
	//Touch behaviour
	void onTouch();
	
	//New act behaviour
	void onAct(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections);
	
	int getInfluenceAmount();
	
	int getDirection(); //Refactor out
	
	public Coordinates getCoordDirection();
	
}
