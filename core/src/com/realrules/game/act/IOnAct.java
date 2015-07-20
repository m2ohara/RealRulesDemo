package com.realrules.game.act;

import java.util.ArrayList;

import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.CoordinateSystem.Coordinates;

public interface IOnAct {
	
	public void performActing(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections);
	
	public int getCurrentDirection();
	
	public int getCurrentAngle();

}
