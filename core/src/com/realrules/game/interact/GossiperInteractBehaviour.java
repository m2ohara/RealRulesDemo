package com.realrules.game.interact;

import java.util.Random;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.InteractSprite;

public class GossiperInteractBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private float promoteOpposeProb = 0.5f;
	private Random rand = new Random();
	private float interactionStateLength = 3f;
	private int interactionStages = 3;
	private InteractSprite interactSprite;
	private IInteractionType interactionType;
	
	@Override
	public void interact(GameSprite interactor, GameSprite interactee) {
		
		//Influence if interactee is neutral and interactor isn't already interacting
		if(!interactor.isManualInteractor && !interactor.isInteracting && interactee.status == 0 && interactee.isActive && rand.nextFloat() > interactSuccess) {
			setInteractionResult(interactor, interactee);
			
			interactor.isInteracting = true; //TODO: Replace with isInteracting
			interactee.isActive = false;
			interactSprite = new InteractSprite(interactionStateLength, interactionStages, interactor, interactionType);
			interactSprite.setAction();

		}
		
	}
	
	private void setInteractionResult(GameSprite interactor, GameSprite interactee) {
		
		if(interactee.status == 0 && interactee.isActive == true) {
			//Oppose
			if(rand.nextFloat() > promoteOpposeProb) {
				interactionType = new OpposerInteractionType(interactor, interactee);
			}
			//Promote
			else {
				interactionType = new SupporterInteractionType(interactor, interactee);
			}
		}
	}
	
}
