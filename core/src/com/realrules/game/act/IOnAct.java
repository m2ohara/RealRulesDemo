package com.realrules.game.act;

import com.realrules.game.demo.HeadSprite;

public interface IOnAct {
	
	public void performActing(float delta, HeadSprite actor);
	
	public int getCurrentDirection();
	
	public int getCurrentAngle();

}
