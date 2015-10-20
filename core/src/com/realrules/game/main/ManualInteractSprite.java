package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ManualInteractSprite extends InteractSprite {
	
	private HeadSprite interactor;
	private HeadSprite interactee;
	private int interactType;

	public ManualInteractSprite(float interactionStateLength,
			int interactionStages, HeadSprite interactor, HeadSprite interactee, int interactType) {
		super(interactionStateLength, interactionStages, interactor);
		
		System.out.println("Setting interact sprite for "+interactor.getXGameCoord()+", "+interactor.getYGameCoord()+ " status: "+interactor.status);
		this.interactor = interactor;
		this.interactee = interactee;
		this.interactType = interactType;
		
		if(interactor.status == 1) {
			setAction();
		}
		else {
			scaleAction = null;
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(interactor.isActive == true && interactor.status == 1 && scaleAction == null) {
			setAction();
		}
	
		if(interactor.isActive == true && this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor == 1) {
			scaleAction.finish();
			isComplete = true;
			this.remove();
			if(interactee.status == 0) {
				setNextInteractSprite();
			}
			else {
				setToLastFollower();
			}
		}
		else if(interactor.status == 1 && this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor != 1) {
			currentScaleFactor += interactionScaleFactor;
			scaleAction = Actions.scaleTo(currentScaleFactor, currentScaleFactor, interactionStateLength);
			this.addAction(scaleAction);
		}
	}
	
	private void setNextInteractSprite() {
		interactor.status = 2;
		interactee.isActive = true;
		interactee.status = 1;
		setInfluenceSprite(interactee);
	}
	
	
	private void setInfluenceSprite(HeadSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactType));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}
	
	private void setToLastFollower() {
		if(ScoreState.validTouchAction()) {
			interactor.setColor(Color.ORANGE);
		}
		else {
			interactor.setColor(Color.YELLOW);
		}
		interactor.status = 1;
	}

}
