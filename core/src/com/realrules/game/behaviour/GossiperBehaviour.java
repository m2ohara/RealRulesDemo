package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAnimateTalkingAct;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.main.CoordinateSystem;
import com.realrules.game.main.CoordinateSystem.Coordinates;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.touch.GossiperTouchAction;
import com.realrules.game.touch.TouchAction;

public class GossiperBehaviour implements IHeadBehaviour {
	
	//Members
	private boolean isActive = false;
	private float rotateP = 0.8f;
	private float interactP = 0.2f;
	private int influenceAmount = 2;
	private TouchAction onTouch;
	private IOnAct onAct;
	
	public GossiperBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		this.isActive = isActive;
		
		onAct = new OnAnimateTalkingAct(rotateP, interactP, framesPath);
		
		onTouch = new GossiperTouchAction(manInteraction);
		this.onTouch.setInteractorX(x);
		this.onTouch.setInteractorY(y);
		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(onAct.getCurrentDirection(), onAct.getCurrentAngle()));
		
	}

	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}		
		
	}

	@Override
	public void onAct(float delta, HeadSprite actor, ArrayList<Coordinates> invalidDirections) {

		if(isActive) {
			onAct.performActing(delta, actor, invalidDirections);
			
			//Update direction  for touch action
			onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(onAct.getCurrentDirection(), onAct.getCurrentAngle()));
		}
		
	}

	
	public int getDirection() {
		return onAct.getCurrentDirection();
	}
	
	public int getInfluenceAmount() {
		return influenceAmount;
	}

	@Override
	public Coordinates getCoordDirection() {
		return onAct.getCurrentCoordinate();
	}
	
}

