package com.realrules.game.act;

import java.util.ArrayList;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public interface IOnAct {
	
	public void performActing(float delta, GameSprite actor, ArrayList<Orientation> invalidDirections);
	
	public Orientation getCurrentCoordinate(); 

}
