package com.realrules.game.demo;

import java.util.ArrayList;

import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.demo.DemoGame.HeadSprite;

public interface ITouchAction {
	
	
	void setInteractorX(int interactorX);
	
	void setInteractorY(int interactorY);
	
	void setInteractorDir(Coordinates interactorDir);
	
	void interact();

}
