package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.main.CoordinateSystem;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.CoordinateSystem.Coordinates;
import com.realrules.game.touch.InfluencerTouchAction;
import com.realrules.game.touch.TouchAction;

public class InfluencerBehaviour implements IHeadBehaviour {

	//Members
	private boolean isActive = true;
	private float rotateP = 0.8f;
	private float interactP = 0.4f;
	private int influenceAmount = 3;
	private TouchAction onTouch;
	private IOnAct onAct;
	private String actingPack = "promoterFollowerPack.pack";
	
	
	public InfluencerBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		this.isActive = isActive;
		
		onAct = new OnAct(rotateP, interactP, framesPath+actingPack);
		
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
