package com.realrules.game.setup;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.ScoreState.State;

public class VoteGameRules implements IGameRules {
	
	private State currentState = null;
	private Group actors = null;
	private int winVotes;
	private State winState;
	private int totalVotes = 0;
	private int remainingVotes = 0;
	
	public VoteGameRules(State winState, int winVotes, int totalVotes) {
		this.winVotes = winVotes;
		this.winState = winState;
		this.totalVotes = totalVotes;
		this.remainingVotes = winVotes;
		setup();
	}

	@Override
	public void setup() {
		this.currentState = State.PLAYING;
		this.actors = GameProperties.get().getActorGroup();
	}

	@Override
	public void update() {
		
		calculateVotes();
		
	}
	
	private void calculateVotes() {

		int forVotes = 0;
		int againstVotes = 0;
		
		for(Actor a : actors.getChildren()) {
			HeadSprite actor = (HeadSprite) a;
			if(actor.status == 1) {
				if(winState == State.WIN) {
					forVotes+=1;
				}
				if(winState == State.LOSE) {
					againstVotes+=1;
				}			
			}
			if(actor.status == 2) {
				forVotes+=1;
			}
			else if(actor.status == 3) {
				againstVotes+=1;
			}
		}
		
		setGameState(forVotes, againstVotes);
		
		updateRemainingVotes(forVotes, againstVotes);
	}
	
	private void setGameState(int forVotes, int againstVotes) {
		
		if(winState == State.WIN) {
			if(forVotes >= winVotes) {
				currentState =  State.WIN;
			}
			else if((totalVotes - (forVotes + againstVotes)) < (winVotes - forVotes)) {
				currentState =  State.LOSE;
			}
		}
		else if(winState == State.LOSE) {
			if(againstVotes >= winVotes) {
				currentState =  State.WIN;
			}
			else if((totalVotes - (forVotes + againstVotes)) < (winVotes - againstVotes)) {
				currentState =  State.LOSE;
			}
		}
		else {
			currentState =  State.PLAYING;
		}
		
	}
	
	private void updateRemainingVotes(int forVotes, int againstVotes) {
		if(winState == State.WIN) {
			remainingVotes = winVotes - forVotes;
		}
		else if(winState == State.LOSE) {
			remainingVotes = winVotes - againstVotes;
		}
	}
	
	@Override
	public int getRemainingPoints() {
		return remainingVotes;
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
