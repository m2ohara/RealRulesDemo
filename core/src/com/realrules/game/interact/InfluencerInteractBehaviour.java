package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.IInteraction;

public class InfluencerInteractBehaviour implements IInteraction {
	
	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(interactee.status == 0) { // && interactee.isActive == true) {
			if(rand.nextFloat() > interactor.argueSuccessP) {
				interactee.status = 2;
				interactee.setColor(Color.CYAN);
				System.out.println("Follower influenced");
			}
		}
		
	}

}
