package com.realrules.game.behaviour;

import com.realrules.game.act.IOnAct;

public interface IBehaviourProperties {
	
	public float getRotateProbability();
	public float getInteractProbability();
	public int getInfluenceAmount();
	//TODO: Refactor actingType into this class

}
