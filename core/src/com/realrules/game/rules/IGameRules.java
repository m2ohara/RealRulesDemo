package com.realrules.game.rules;

import com.realrules.game.demo.ScoreState.State;

public interface IGameRules {
	
	//Setup game rules
	void setup();
	
	//Update game score
	void update();
	
	//Do action on game completion
	State getCurrentState();
	
	void reset();

}
