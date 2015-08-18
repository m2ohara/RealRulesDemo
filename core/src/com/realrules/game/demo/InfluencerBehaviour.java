package com.realrules.game.demo;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;

public class InfluencerBehaviour implements IHeadBehaviour {

	//Members
	public int status = 0; //0 : neutral, 1 : for 2 : against
//	public float argueSuccessP = 0.2f;
	private float rotateP = 0.8f;
	private int influenceAmount = 3;
	private float interactP = 0.4f;
	private boolean isActive = true;
	private TouchAction onTouch;
	private IOnAct onAct;
	
	
	public InfluencerBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		this.isActive = isActive;
		
		onAct = new OnAct(rotateP, interactP, framesPath);
		
		onTouch = new InfluencerTouchAction(x, y, manInteraction);
		
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

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}

	@Override
	public int getDirection() {
		return onAct.getCurrentDirection();
	}

}
