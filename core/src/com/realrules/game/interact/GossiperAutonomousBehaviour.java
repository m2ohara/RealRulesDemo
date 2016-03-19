package com.realrules.game.interact;

import java.util.Random;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.AutoInteractSprite;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;

public class GossiperAutonomousBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private float promoteOpposeProb = 0.5f;
	private Random rand = new Random();
	private float interactionStateLength = 3f;
	private int interactionStages = 3;
	private AutoInteractSprite interactSprite;
	private IInteractionType interactionType;
	
	@Override
	public void interact(GameSprite interactor, GameSprite interactee) {
		
		//Influence if interactee is neutral and interactor isn't already interacting
		if(!interactor.isInteracting && interactee.interactStatus == Status.NEUTRAL && rand.nextFloat() > interactSuccess) {
			System.out.println("Starting auto interaction for interactor: "+interactor.interactStatus+", interactee "+interactee.interactStatus);
			setInteractionResult(interactor, interactee);
			
			interactor.isInteracting = true;
			interactee.isActive = false;
			interactSprite = new AutoInteractSprite(interactionStateLength, interactionStages, interactor, interactionType);
			interactSprite.setAction();

		}
		
	}
	
	private void setInteractionResult(GameSprite interactor, GameSprite interactee) {
		
		if(interactee.interactStatus == Status.NEUTRAL && interactee.isActive == true) {
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
