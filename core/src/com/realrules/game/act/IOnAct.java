package com.realrules.game.act;

import java.util.ArrayList;

import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public interface IOnAct {
	
	public void performActing(float delta, HeadSprite actor, ArrayList<Orientation> invalidDirections);
	
	public Orientation getCurrentCoordinate(); 

}
