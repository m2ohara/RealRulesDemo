package com.realrules.game.act;

import com.realrules.game.main.WorldSystem.Orientation;

public interface IOnAct {
	
	public void performActing(float delta);
	
	public Orientation getCurrentCoordinate(); 
	
	public void setFrame();

}
