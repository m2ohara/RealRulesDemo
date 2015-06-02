package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class GossiperInteractBehaviour implements IInteraction {

	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(interactee.status == 0 && interactee.isActive == true) {
			if(rand.nextFloat() > interactor.argueSuccessP) {
				interactee.status = 3;
				interactee.setColor(Color.GRAY);
//				System.out.println("Opposer influenced");
			}
			else {
				interactee.status = 1;
				interactee.setColor(Color.CYAN);
//				System.out.println("Follower influenced");
			}
		}
		
	}
	
}
