package com.realrules.game.interact;

import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;

public class Interactee {
	
	public GameSprite getInteractee(int xGameCoord, int yGameCoord, Orientation orientation) {
		
		GameSprite interactee = null;
		
		// If facing towards the right
		if (orientation == Orientation.E
				&& (xGameCoord + 1) < WorldSystem
						.get().getGameXCoords().size()) {
			// Get interactee by coordinates
			interactee = WorldSystem.get().getMemberFromCoords(
					xGameCoord + 1,
					(yGameCoord));
	//		System.out.println("Member type " + interactor.status
	//				+ "  influencing to the right at "
	//				+ (xGameCoord + 1) + ", "
	//				+ yGameCoord);
	
		}
		if (orientation == Orientation.N
				&& (yGameCoord - 1) > -1) {
			interactee = WorldSystem.get().getMemberFromCoords(
					xGameCoord,
					(yGameCoord - 1));
	//		System.out.println("Member type " + interactor.status
	//				+ "  influencing above at "
	//				+ xGameCoord + ", "
	//				+ (yGameCoord - 1));
	
		}
		if (orientation == Orientation.S
				&& (yGameCoord + 1) < WorldSystem
						.get().getGameYCoords().size()) {
			interactee = WorldSystem.get().getMemberFromCoords(
					xGameCoord,
					(yGameCoord + 1));
	//		System.out.println("Member type " + interactor.status
	//				+ "  influencing below at "
	//				+ xGameCoord + ", "
	//				+ (yGameCoord + 1));
		}
		if (orientation == Orientation.W
				&& (xGameCoord - 1) > -1) {
			interactee = WorldSystem.get().getMemberFromCoords(
					xGameCoord - 1,
					(yGameCoord));
	//		System.out.println("Member type " + interactor.status
	//				+ " influencing to the left at "
	//				+ (xGameCoord + 1) + ", "
	//				+ yGameCoord);
		}
		
		return interactee;
	}

	public boolean isInteracteeNeutral(int xGameCoord, int yGameCoord, Orientation orientation) {
		
		GameSprite interactee = getInteractee(xGameCoord, yGameCoord, orientation);

		if(interactee != null && interactee.status == 0) {
			return true;
		}
		
		return false;
	}
}
