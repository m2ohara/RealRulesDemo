package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InfluenceType;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.SpriteOrientation;
import com.realrules.game.touch.TouchAction;

public class Behaviour implements ISpriteBehaviour {

	//Members
	private boolean isActive = true;
	private TouchAction onTouch;
	public IOnAct actType;
	private SpriteOrientation changeOrientation;
	private IBehaviourProperties properties;
	
	public Behaviour(boolean isActive, IOnAct onAct, TouchAction touchAction, IBehaviourProperties properties, SpriteOrientation changeOrientation) {
		
		this.isActive = isActive;
		this.actType = onAct;
		this.onTouch = touchAction;
		
		this.changeOrientation = changeOrientation;
		this.properties = properties;
	}
	

	
	@Override
	public void onTouch() {
		
//		if(isActive) {
////			onTouch.onAction();
//		}
		if(GameProperties.get().isTapAllowed(this.hashCode()) && changeOrientation.cyclicChange()) {
			actType.changeSpriteOrientation();
			GameProperties.get().updateTapCount(this.hashCode());
		}
		
	}

	@Override
	public void onAct(float delta, GameSprite actor, ArrayList<Orientation> invalidDirections) {
		
		if(isActive) {
			
			actType.performActing(delta);		
		}
		
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
	
	public InfluenceType getInfluenceType() {
		return properties.getInfluenceType();
	}
}
