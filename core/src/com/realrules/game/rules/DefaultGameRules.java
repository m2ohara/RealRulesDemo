package com.realrules.game.rules;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.ScoreState.State;

public class DefaultGameRules implements IGameRules {
	
	public State currentState = State.PLAYING;
	
	private Group actors = null;
	private int totalPoints = 0;
	
	public DefaultGameRules(int totalPoints) {
		this.totalPoints = totalPoints;
		setup();
	}

	@Override
	public void setup() {
		this.actors = GameProperties.get().getActorGroup();
	}

	@Override
	public void update() {

		int forPoints = 0;
		int againstPoints = 0;
		
		for(Actor a : actors.getChildren()) {
			HeadSprite actor = (HeadSprite) a;
			if(actor.status == 1 || actor.status == 2) {
				forPoints+=1;
			}
			else if(actor.status == 3) {
				againstPoints+=1;
			}
		}
		
		setZeroSumGameState(forPoints, againstPoints); 
		
	}
	
	private void setZeroSumGameState(int forPoints, int againstPoints) {
		if(forPoints > (againstPoints + (totalPoints - (forPoints + againstPoints)))) {
			currentState =  State.WIN;
		}
		else if(againstPoints > (forPoints + (totalPoints - (forPoints + againstPoints)))) {
			//Loss
			currentState =  State.LOSE;
		}
		else if((forPoints + againstPoints == totalPoints) && forPoints == againstPoints) {
			//Draw
			currentState =  State.DRAW;
		}
		else {
			currentState =  State.PLAYING;
		}
		
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
