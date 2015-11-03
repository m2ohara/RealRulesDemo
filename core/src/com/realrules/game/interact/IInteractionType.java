package com.realrules.game.interact;

import com.realrules.game.main.GameSprite;

public interface IInteractionType {
	
	void setToMiddleFollower(GameSprite hitActor);
	
	void setInfluencedSprite(GameSprite interactee);
	
	void complete();

}
