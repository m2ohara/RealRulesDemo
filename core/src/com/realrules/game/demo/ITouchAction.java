package com.realrules.game.demo;

import com.realrules.game.demo.CoordinateSystem.Coordinates;

public interface ITouchAction {
	
	
	void setInteractorX(int interactorX);
	
	void setInteractorY(int interactorY);
	
	void setInteractorDir(Coordinates interactorDir);

}
