package com.realrules.game.interact;

import java.util.Random;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.AutoInteractSprite;

public class DeceiverAutonomousBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private Random rand = new Random();
	private float interactionStateLength = 4f;
	private int interactionStages = 3;
	private AutoInteractSprite interactSprite;
	private IInteractionType interactionType;

	@Override
	public void interact(GameSprite interactor, GameSprite interactee) {
		
		//Influence if interactee is neutral and interactor isn't already interacting
		if(!interactor.isIntermediateInteractor && !interactor.isInteracting && interactee.status == 0 && interactee.isActive && rand.nextFloat() > interactSuccess) {
			setInteractionResult(interactor, interactee);
			
			interactor.isInteracting = true;
			interactee.isActive = false;
			interactSprite = new AutoInteractSprite(interactionStateLength, interactionStages, interactor, interactionType);
			interactSprite.setAction();

		}
		
	}
	
	private void setInteractionResult(GameSprite interactor, GameSprite interactee) {
		
		if(interactee.status == 0 && interactee.isActive == true) {
			//Oppose
			interactionType = new OpposerInteractionType(interactor, interactee);
		}
	}
	
}
