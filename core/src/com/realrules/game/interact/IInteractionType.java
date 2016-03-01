package com.realrules.game.interact;

import com.realrules.game.main.GameSprite;

public interface IInteractionType {
	
	void setStatus();
	
	void setInfluencedSprite();
	
	void complete();
	
	void setInteracts(GameSprite interactor, GameSprite interactee);

}
