package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAnimateTalkingAct;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.touch.DeceiverTouchAction;
import com.realrules.game.touch.TouchAction;

public class DeceiverBehaviour implements ISpriteBehaviour {
	
	//Members
	private boolean isActive = true;
	private float rotateP = 0.8f;
	private float interactP = 0.4f;
	private int influenceAmount = 3;
	private TouchAction onTouch;
	private IOnAct actType;

	
	public DeceiverBehaviour(boolean isActive, String framesPath, int x, int y, IInteractionType manInteraction, GameSprite actor, ArrayList<Orientation> validDirections) {
		this.isActive = isActive;
		
		actType = new OnAnimateTalkingAct(rotateP, interactP, framesPath, actor, validDirections);
		
		this.onTouch = new DeceiverTouchAction(manInteraction, x, y);
		this.onTouch.setInteractorX(x);
		this.onTouch.setInteractorY(y);
		this.onTouch.setInteractorDir(actType.getCurrentCoordinate());
		
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
