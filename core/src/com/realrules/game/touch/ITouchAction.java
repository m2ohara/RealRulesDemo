package com.realrules.game.touch;

import com.realrules.game.main.CoordinateSystem.Coordinates;

public interface ITouchAction {
	
	
	void setInteractorX(int interactorX);
	
	void setInteractorY(int interactorY);
	
	void setInteractorDir(Coordinates interactorDir);
	
	void interact();

}
