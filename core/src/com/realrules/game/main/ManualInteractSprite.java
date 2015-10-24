package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.ManualSupporterInteraction;

public class ManualInteractSprite extends Image{
	
	private static String framesPath = "sprites//Meep//Effects//Effects.pack";
	public boolean isInteracting = false;
	protected float interactionScaleFactor;
	protected float currentScaleFactor;
	protected float interactionStateLength;
	protected ScaleToAction scaleAction;
	
	private HeadSprite interactor;
	private HeadSprite interactee;
	private IManualInteraction interactionType;

	public ManualInteractSprite(float interactionStateLength, int interactionStages, HeadSprite interactor, HeadSprite interactee, IManualInteraction interactionType) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		this.interactionType = interactionType;
		this.interactionStateLength = interactionStateLength;
		this.interactionScaleFactor = 1f/(float)(interactionStages);
		this.currentScaleFactor = interactionScaleFactor;
		setSprite(interactor.getStartingX()+35, interactor.getStartingY()+35);
		
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0))));
		
		this.interactor = interactor;
		this.interactee = interactee;
		
		if(interactor.status == 1) {
			setAction();
			interactionType.setInfluencedSprite(interactor);
			interactor.setColor(Color.WHITE);
		}
		else {
			scaleAction = null;
		}
	}
	
	private void setSprite(float xCoord, float yCoord) {

		this.setPosition(xCoord, yCoord);
		this.setTouchable(Touchable.disabled);
		this.scaleBy(-1);

		GameProperties.get().addActorToStage(this);
	}
	
	public void setAction() {
		scaleAction = Actions.scaleTo(currentScaleFactor, currentScaleFactor, interactionStateLength);
		this.addAction(scaleAction);
		this.isInteracting = true;
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
			if(interactee.isManualInteractor) {
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
		interactionType.setInfluencedSprite(interactee);
//		setInfluenceSprite(interactee);
	}
	
//	
//	private void setInfluenceSprite(HeadSprite interactee) {
//		
//		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactType));
//
//		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
//		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
//		
//		GameProperties.get().addActorToStage(handSign);
//	}
	
	private void setToLastFollower() {
		if(ScoreState.validTouchAction()) {
			interactee.setColor(Color.ORANGE);
		}
		else {
			interactee.setColor(Color.YELLOW);
		}
		interactee.status = 1;
		interactee.isActive = true;
	}
	
	protected boolean isComplete = false;
	public boolean isComplete() {
		return isComplete;
	}

}
