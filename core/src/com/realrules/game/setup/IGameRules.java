package com.realrules.game.setup;

import com.realrules.game.main.ScoreState.State;

public interface IGameRules {
	
	//Setup game rules
	void setup();
	
	//Update game score
	void update();
	
	State getCurrentState();
	
	int getRemainingPoints();
	
	int getEndScore();
	
	void reset();

}
