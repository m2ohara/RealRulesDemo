package com.realrules.game.act;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.InteractSprite;

//TODO: Not Implemented, remove
public class OnAnimateInteractionAct implements IOnActing {
	
	private float interactionStateLength;
	private float interactionStateTime = 0;
	private int interactionStages;
	private float interactionFactor;
	private Array<AtlasRegion> interactFrames;
	private InteractSprite interactSprite;

	private float frameLength = 0.2f * GameProperties.get().getUniversalTimeRatio();
	private float frameTime = frameLength;
	private int frameCount = 0;
	
	public OnAnimateInteractionAct(float interactionStateLength, int interactionStages, InteractSprite interactSprite) {
		this.interactionStateLength = interactionStateLength;
		this.interactionStages = interactionStages;
		this.interactionFactor = interactionStateLength/interactionStages;
		this.interactSprite = interactSprite;
		
	}

	@Override
	public void performActing(float delta) { 
		
//		if(interactFrames == null) {
//			interactFrames = Assets.get().getAssetManager().get("sprites//Meep//Effects//Effects.pack", TextureAtlas.class).getRegions();
//			setInteractionSprite();
//		}
//		
		updateSprite(delta);
		updateInteractionState(delta);

	}
	
//	private void setInteractionSprite() {
//		
//		
//		actor = new Image(interactFrames.get(0));
//
//		actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
//		actor.setPosition(interactSprite.getStartingX(), interactSprite.getStartingY());
//		actor.setTouchable(Touchable.disabled);
//		actor.scaleBy(-interactionFactor);
//		
//		GameProperties.get().addActorToStage(actor);
//	}
	
	private void updateSprite(float delta) {
		
		if(frameTime >= frameLength) {
			frameTime = 0.0f;
			
			if(frameCount > interactFrames.size -1) {
				frameCount = 0;
			}
			
			interactSprite.setDrawable(new TextureRegionDrawable(new TextureRegion(interactFrames.get(frameCount++))));
			
		}
		frameTime += delta;
	}
	
	private void updateInteractionState(float delta) {
		//Perform interaction animation
		if(interactionStateTime  < interactionStateLength) {
			if(interactionStateTime % interactionFactor == 0) {
				interactSprite.scaleBy(1);
			}
		}
		//Flag status as finished interaction
		else {
			interactionStateTime  = 0.0f;
			interactSprite.isInteracting = false;
		}
		
		interactionStateTime += delta;
		
	}

}
