package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.TouchAction;

public class Behaviour implements ISpriteBehaviour {

	//Members
	private boolean isActive = true;
	private int influenceAmount;
	private TouchAction onTouch;
	public IOnAct actType;
	
	public Behaviour(boolean isActive, IOnAct onAct, TouchAction touchAction, IBehaviourProperties properties) {

		this.influenceAmount = properties.getInfluenceAmount();
		
		this.isActive = isActive;
		this.actType = onAct;
		this.onTouch = touchAction;
		
	}
	
	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}
		
	}

	@Override
	public void onAct(float delta, GameSprite actor, ArrayList<Orientation> invalidDirections) {
		
		if(isActive) {
			actType.performActing(delta);
			
			//Update direction  for touch action
			onTouch.setInteractorDir(actType.getCurrentCoordinate());
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	
	@Override
	public Orientation getOrientation() {
		return actType.getCurrentCoordinate();
	}

	@Override
	public IOnAct getActType() {
		return this.actType;
	}
}
