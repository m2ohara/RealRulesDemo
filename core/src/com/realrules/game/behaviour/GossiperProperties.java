package com.realrules.game.behaviour;

import java.util.Random;

import com.realrules.game.main.GameSprite.InfluenceType;


public class GossiperProperties implements IBehaviourProperties {
	
	private float rotateProbability = 0.8f;
	private float interactProbability = 0.2f;
	private int influenceAmount = 2;
	private Random rand = new Random();
	
	public float getRotateProbability() {
		return rotateProbability;
	}
	public float getInteractProbability() {
		return interactProbability;
	}
	public int getInfluenceAmount() {
		return influenceAmount;
	}
	public InfluenceType getInfluenceType() {
		return InfluenceType.values()[rand.nextInt(2)];
	}

}
