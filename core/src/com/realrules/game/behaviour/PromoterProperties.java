package com.realrules.game.behaviour;

public class PromoterProperties implements IBehaviourProperties {

	private float rotateP = 0.8f;
	private float interactP = 0.4f;
	private int influenceAmount = 3;
	private int influenceType = 2;

	@Override
	public float getRotateProbability() {
		return rotateP;
	}

	@Override
	public float getInteractProbability() {
		return interactP;
	}

	@Override
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	
	@Override
	public int getInfluenceType() {
		return influenceType;
	}

}
