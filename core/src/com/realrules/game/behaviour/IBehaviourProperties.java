package com.realrules.game.behaviour;

import com.realrules.game.main.GameSprite.InfluenceType;


public interface IBehaviourProperties {
	
	public float getRotateProbability();
	public float getInteractProbability();
	
	public int getInfluenceAmount(); //TODO: Remoe. Not in use
	public InfluenceType getInfluenceType();
	
	//TODO: Refactor actingType into this class

}
