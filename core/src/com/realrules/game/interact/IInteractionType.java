package com.realrules.game.interact;

import com.realrules.game.main.GameSprite;

public interface IInteractionType {
	
	void setInteractorStatus(GameSprite interactor);
	
	void setInfluencedSprite(GameSprite interactee);
	
	void complete();

}
