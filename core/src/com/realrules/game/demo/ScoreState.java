package com.realrules.game.demo;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ScoreState {
	
	public enum State {PLAYING, WIN, LOSE, DRAW}
	
	static int totalPoints = 0;
	private static int userPoints = 6;
	private static int touchActionPoints = 0;
	
	public static boolean IsPlaying () {
		return totalPoints == 0 ? false : true;
	}
	
	public static void setTotalPoints(int _totalPoints) {
		totalPoints = _totalPoints;
	}
	
	public static int getTotalPoints() {
		return totalPoints;
	}
	
	public static State getScoreState(Group actors) {
		
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
		
		//Playing
		return State.PLAYING;
	}

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

}
