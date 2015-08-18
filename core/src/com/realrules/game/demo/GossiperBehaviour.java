package com.realrules.game.demo;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;

public class GossiperBehaviour implements IHeadBehaviour {
	
	private boolean isActive = false;
	private int direction = 0;
	private float rotateP = 0.8f;
	private float interactSuccess = 0.2f;
	private int influenceAmount = 2;
	private TouchAction onTouch;
	private IOnAct onAct;
	
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	
	public GossiperBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		this.isActive = isActive;
		onTouch = new GossiperTouchAction(manInteraction);
		this.onTouch.setInteractorX(x);
		this.onTouch.setInteractorY(y);
		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, 0));
		
		onAct = new OnAct(rotateP, interactSuccess, framesPath);
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
	
}

