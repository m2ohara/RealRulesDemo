package com.realrules.game.behaviour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.realrules.game.act.IOnAct;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.SpriteOrientation;
import com.realrules.game.touch.TouchAction;

public class Behaviour implements ISpriteBehaviour {

	//Members
	private boolean isActive = true;
	private int influenceAmount;
	private TouchAction onTouch;
	public IOnAct actType;
	private SpriteOrientation changeOrientation;
	
	public Behaviour(boolean isActive, IOnAct onAct, TouchAction touchAction, IBehaviourProperties properties, SpriteOrientation changeOrientation) {

		this.influenceAmount = properties.getInfluenceAmount();
		
		this.isActive = isActive;
		this.actType = onAct;
		this.onTouch = touchAction;
		
		this.changeOrientation = changeOrientation;
	}
	

	
	@Override
	public void onTouch() {
		
		if(isActive) {
//			onTouch.onAction();
		}
		if(changeOrientation.cyclicChange()) {
			actType.changeSpriteOrientation();
		}
		
	}

	@Override
	public void onAct(float delta, GameSprite actor, ArrayList<Orientation> invalidDirections) {
		
		if(isActive) {
			
			actType.performActing(delta);		
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	
	//Orientation logic
	@Override
	public Orientation getOrientation() {
		return changeOrientation.getOrientation();
	}
	
	public boolean changeOrientationOnInvalid() {
		if(changeOrientation.cyclicChangeOnInvalidInteractee()) {
			actType.changeSpriteOrientation();	
			return true;
		}
		return false;
	}
	
	public void changeOrientation() {
		changeOrientation.onCyclicChange();
		actType.changeSpriteOrientation();	
	}
}
