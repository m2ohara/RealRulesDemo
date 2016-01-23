package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.state.PlayerState;
import com.realrules.game.state.GameScoreState;

public class SwipeInteractSprite extends Image{
	
	private static String framesPath = "sprites//Meep//Effects//Effects.pack";
	public boolean isInteracting = false;
	protected float interactionScaleFactor;
	protected float currentScaleFactor;
	protected float interactionStateLength;
	protected ScaleToAction scaleAction;
	protected float finalScale;
	
	private GameSprite interactor;
	private GameSprite interactee;
	private IInteractionType interactionType;

	public SwipeInteractSprite(float interactionStateLength, int interactionStages, GameSprite interactor, GameSprite interactee, IInteractionType interactionType) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		this.interactionType = interactionType;
		
		//Set interaction length based on level - faster for higher difficulty
		this.interactionStateLength = (float)(interactionStateLength - (PlayerState.get().getLevel()/2));
		if(this.interactionStateLength < 1) { this.interactionStateLength = 1; }
		
		//Scale sprite based on level - smaller for higher difficulty
		finalScale = WorldSystem.get().getLevelScaleFactor();
		
		this.interactionScaleFactor = finalScale/(float)(interactionStages);
		this.currentScaleFactor = interactionScaleFactor;
		
		setSprite(interactor.getStartingX()+35, interactor.getStartingY()+35);	
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0))));
		
		this.interactor = interactor;
		this.interactee = interactee;
		
		if(interactor.status == 1) {
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
	
	public void startInteraction() {

		interactor.isActive = true;
		GameProperties.get().IsSwipeInteraction = true;
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
	
		if(interactor.isActive == true && this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor >= finalScale) {
			scaleAction.finish();
			isComplete = true;
			this.remove();
			if(interactee.isManualInteractor) {
				setNextInteractSprite();
			}
			else {
				GameProperties.get().IsSwipeInteraction = false;
				setToLastFollower();
			}
		}
		else if(interactor.status == 1 && this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor < finalScale) {
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
		interactor.isManualInteractor = false;
	}
	
	
	private void setToLastFollower() {
		if(GameScoreState.validTouchAction()) {
			interactee.setColor(Color.ORANGE);
		}
		else {
			interactee.setColor(Color.YELLOW);
		}
		interactee.setOrientation();
		interactee.status = 1;
		interactee.isActive = true;
		interactor.isManualInteractor = false;
	}
	
	protected boolean isComplete = false;
	public boolean isComplete() {
		return isComplete;
	}

}
