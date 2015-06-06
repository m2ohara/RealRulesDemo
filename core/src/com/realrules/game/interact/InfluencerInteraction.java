package com.realrules.game.interact;

import com.realrules.game.demo.AutonomousInteraction;

public class InfluencerInteraction extends AutonomousInteraction {
	
	public InfluencerInteraction() {
		this.interactionBehaviour = new InfluencerInteractBehaviour();
	}

}
