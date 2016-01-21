package com.realrules.game.behaviour;


public class GossiperProperties implements IBehaviourProperties {
	
	private float rotateProbability = 0.8f;
	private float interactProbability = 0.2f;
	private int influenceAmount = 2;
	
	public float getRotateProbability() {
		return rotateProbability;
	}
	public float getInteractProbability() {
		return interactProbability;
	}
	public int getInfluenceAmount() {
		return influenceAmount;
	}

}
