package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.act.IOnActing;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.InteractSprite;

public class GossiperInteractBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private float promoteOpposeProb = 0.5f;
	private Random rand = new Random();
	private float interactionStateLength = 3f;
	private int interactionStages = 3;
	private InteractSprite interactSprite;
	private IManualInteraction interactionType;
	
	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		
		//Influence if interactee is neutral and interactor isn't already interacting
		if(!interactor.isManualInteractor && !interactor.isInteracting && interactee.status == 0 && interactee.isActive && rand.nextFloat() > interactSuccess) {
			setInteractionResult(interactor, interactee);
			
			interactor.isInteracting = true; //TODO: Replace with isInteracting
			interactee.isActive = false;
			interactSprite = new InteractSprite(interactionStateLength, interactionStages, interactor, interactionType);
			interactSprite.setAction();

		}
		
	}
	
	private void setInteractionResult(HeadSprite interactor, HeadSprite interactee) {
		
		if(interactee.status == 0 && interactee.isActive == true) {
			//Oppose
			if(rand.nextFloat() > promoteOpposeProb) {
				interactionType = new ManualOpposerInteraction(interactor, interactee);
			}
			//Promote
			else {
				interactionType = new ManualSupporterInteraction(interactor, interactee);
			}
		}
	}
	
}
