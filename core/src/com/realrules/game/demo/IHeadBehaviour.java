package com.realrules.game.demo;

import java.util.ArrayList;

import com.realrules.game.demo.CoordinateSystem.Coordinates;

public interface IHeadBehaviour {
	
	//Touch behaviour
	void onTouch();
	
	//New act behaviour
	void onAct(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections);
	
	int getInfluenceAmount();
	
	int getDirection(); //Refactor out
	
}
