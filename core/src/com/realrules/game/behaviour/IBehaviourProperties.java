package com.realrules.game.behaviour;


public interface IBehaviourProperties {
	
	public float getRotateProbability();
	public float getInteractProbability();
	
	public int getInfluenceAmount(); //TODO: Remoe. Not in use
	public int getInfluenceType();
	
	//TODO: Refactor actingType into this class

}
