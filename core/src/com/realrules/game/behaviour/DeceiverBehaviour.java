package com.realrules.game.behaviour;

import java.util.ArrayList;

import com.realrules.game.act.IOnAct;
import com.realrules.game.act.OnAct;
import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.touch.DeceiverTouchAction;
import com.realrules.touch.TouchAction;

public class DeceiverBehaviour  implements IHeadBehaviour {
	
	//Members
	public int status = 0; //0 : neutral, 1 : for 2 : against
	private int influenceAmount = 3;
	public float argueSuccessP = 0.2f;
	private boolean isActive = true;
	private float rotateP = 0.8f;
	private int direction; //0 : right, 1 : left
	private TouchAction onTouch;
	private IOnAct onAct;

	
	public DeceiverBehaviour(boolean isActive, String framesPath, int x, int y, IManualInteraction manInteraction) {

		this.direction = 0;
		this.isActive = isActive;
		
		this.onTouch = new DeceiverTouchAction(manInteraction);
		this.onTouch.setInteractorX(x);
		this.onTouch.setInteractorY(y);
		this.onTouch.setInteractorDir(CoordinateSystem.getCoordDirection(direction, 0));
		
		onAct = new OnAct(rotateP, argueSuccessP, framesPath);
	}

	@Override
	public void onTouch() {
		
		if(isActive) {
			onTouch.interact();
		}
		
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
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
