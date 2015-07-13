package com.realrules.game.demo;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.realrules.game.setup.IGameRules;
import com.realrules.game.setup.VoteGameRules;

public class ScoreState {
	
	public enum State {PLAYING, WIN, LOSE, DRAW}
	
	static int totalPoints = 0;
	private static int touchActionPoints = 4;
	private static int userPoints = touchActionPoints;
	private IGameRules scoreSystem = null;
	private boolean isPlaying = false;
	
	public ScoreState(int winVotes, State winState, int totalVotes) {
		scoreSystem = new VoteGameRules(winState, winVotes, totalVotes);
	}
	
	public static int getTouchActionPoints() {
		return touchActionPoints;
	}
	
	//Redundant code
	public static boolean IsPlaying () {
		return totalPoints == 0 ? false : true;
	}
	
	public static void setTotalPoints(int _totalPoints) {
		totalPoints = _totalPoints;
	}
	
	public static int getTotalPoints() {
		return totalPoints;
	}
	
	public static State getZeroSumScoreState(Group actors) {
		
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
		
		//Playing
		return getZeroSumGameState(forPoints, againstPoints); 
	}
	
	private static State getZeroSumGameState(int forPoints, int againstPoints) {
		if(forPoints > (againstPoints + (totalPoints - (forPoints + againstPoints)))) {
			return State.WIN;
		}
		else if(againstPoints > (forPoints + (totalPoints - (forPoints + againstPoints)))) {
			//Loss
			return State.LOSE;
		}
		else if((forPoints + againstPoints == totalPoints) && forPoints == againstPoints) {
			//Draw
			return State.DRAW;
		}
		return State.PLAYING;
		
	}
	//**************************

	public static boolean validTouchAction() {
		if(userPoints >= touchActionPoints) {
			return true;
		}
		return false;
	}

	public static void addUserPoints(int _userPoints) {
		userPoints += _userPoints;
	}
	
	public static int getUserPoints() {
		return userPoints;
	}
	
	public static void resetUserPoints() {
		userPoints = 0;
	}
	
	public void update() {
		scoreSystem.update();
	}
	
	public State getCurrentState() {
		return scoreSystem.getCurrentState();
	}

}
