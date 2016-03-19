package com.realrules.game.interact.individual;

import com.realrules.game.interact.IInteractionType;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;

public class IndividualInteraction implements IInteractionType {
	private GameSprite interactor;
	private GameSprite interactee;
	private IIndividualInteraction interactorInteraction;
	private IIndividualInteraction interacteeInteraction;
	
	public IndividualInteraction() {}
	
	@Override
	public void setInteracts(GameSprite interactor, GameSprite interactee) {
		this.interactor = interactor;
		this.interactee = interactee;
	}
	
	private void setInteraction() {
		if(interactor.interactorType == InteractorType.First) {
			interactorInteraction = new FirstInteraction(interactor);
		}
		
		if(interactor.interactorType == InteractorType.Intermediate) {
			interactorInteraction = new IntermediateInteraction(interactor, interactor.influenceType);
		}
		
		if(interactee.interactorType == InteractorType.Intermediate) {
			interacteeInteraction = new IntermediateInteraction(interactee, interactor.influenceType);
		}
		
		if(interactee.interactorType == InteractorType.Last) {
			interacteeInteraction = new LastInteraction(interactee, interactor.influenceType);
		}
	}


	@Override
	public void setStatus() {
		if(interactorInteraction == null) {
			setInteraction();
		}
		
		if(interactorInteraction != null) {
			interactorInteraction.setStatus();
		}
		
		if(interacteeInteraction != null) {
			interacteeInteraction.setStatus();
		}
		
	}


	@Override
	public void setInfluencedSprite() {
		if(interactorInteraction == null) {
			setInteraction();
		}
		
		if(interactorInteraction != null) {
			interactorInteraction.setInfluencedSprite();;
		}
		
		if(interacteeInteraction != null) {
			interacteeInteraction.setInfluencedSprite();;
		}
		
	}


	@Override
	public void complete() {
		interactee.influenceType = interactor.behaviour.getInfluenceType();
		interactee.interactStatus = Status.INFLUENCED;
		interactor.changeOrientationOnInvalid();
		interactor.isInteracting = false;
		interactee.isActive = true;
		setInfluencedSprite();
	}

}