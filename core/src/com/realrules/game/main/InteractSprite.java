package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.ManualSupporterInteraction;

public class InteractSprite extends Image {
	
	private static String framesPath = "sprites//Meep//Effects//Effects.pack";
	public boolean isInteracting = false;
	protected float interactionScaleFactor;
	protected float currentScaleFactor;
	protected float interactionStateLength;
	protected ScaleToAction scaleAction;
	private IManualInteraction interactionType;
	
	public InteractSprite(float interactionStateLength, int interactionStages, HeadSprite interactor, IManualInteraction interactionType) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		this.interactionType = interactionType;
		this.interactionStateLength = interactionStateLength;
		this.interactionScaleFactor = 1f/(float)(interactionStages);
		this.currentScaleFactor = interactionScaleFactor;
		setSprite(interactor.getStartingX()+35, interactor.getStartingY()+35);
		
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0))));
	}
	
	private void setSprite(float xCoord, float yCoord) {

//		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
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
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	
		if(this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor == 1) {
			scaleAction.finish();
			isComplete = true;
			interactionType.complete();
			this.remove();
		}
		else if(this.scaleAction != null && this.getActions().size == 0 && currentScaleFactor != 1) {
			currentScaleFactor += interactionScaleFactor;
			scaleAction = Actions.scaleTo(currentScaleFactor, currentScaleFactor, interactionStateLength);
			this.addAction(scaleAction);
		}
	}
	
	protected boolean isComplete = false;
	public boolean isComplete() {
		return isComplete;
	}
	
}

