package com.realrules.game.touch;

import com.realrules.game.main.WorldSystem.Orientation;

public interface ITouchAction {
	
	
	void setInteractorX(int interactorX);
	
	void setInteractorY(int interactorY);
	
	void setInteractorDir(Orientation interactorDir);
	
	void interact();

}
