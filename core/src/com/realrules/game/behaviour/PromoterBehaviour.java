package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAnimateTalkingAct;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.PromoterTouchAction;
import com.realrules.game.touch.TouchAction;

public class PromoterBehaviour implements IHeadBehaviour {

	//Members
	private boolean isActive = true;
	private float rotateP = 0.8f;
	private float interactP = 0.4f;
	private int influenceAmount = 3;
	private TouchAction onTouch;
	private IOnAct onAct;
	
	
	public PromoterBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {
		this.isActive = isActive;
		
		onAct = new OnAnimateTalkingAct(rotateP, interactP, framesPath);
		
		onTouch = new PromoterTouchAction(x, y, manInteraction);
		
	}
	
	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}
		
	}

	@Override
	public void onAct(float delta, HeadSprite actor, ArrayList<Orientation> invalidDirections) {
		
		if(isActive) {
			onAct.performActing(delta, actor, invalidDirections);
			
			//Update direction  for touch action
			onTouch.setInteractorDir(onAct.getCurrentCoordinate());
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	
	@Override
	public Orientation getOrientation() {
		return onAct.getCurrentCoordinate();
	}

}