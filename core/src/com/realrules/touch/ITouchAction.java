package com.realrules.touch;

import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.CoordinateSystem.Coordinates;

public interface ITouchAction {
	
	
	void setInteractorX(int interactorX);
	
	void setInteractorY(int interactorY);
	
	void setInteractorDir(Coordinates interactorDir);
	
	void interact();

}
