package com.realrules.game.interact;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public class AutonomousInteraction {
	
	public IInteraction interactionBehaviour = null;

	public void interact(GameSprite interactor, Group actorGroup, Orientation orientation) {
//		System.out.println("Interactor with status "+interactor.status);
		// As long as interactor isn't neutral
		if (interactor.status > 1) {
			GameSprite interactee = null;

			// If facing towards the right
			if (orientation == Orientation.E
					&& (interactor.getXGameCoord() + 1) < WorldSystem
							.get().getGameXCoords().size()) {
				// Get interactee by coordinates
				interactee = WorldSystem.get().getMemberFromCoords(
						interactor.getXGameCoord() + 1,
						(interactor.getYGameCoord()));
//				System.out.println("Member type " + interactor.status
//						+ "  influencing to the right at "
//						+ (interactor.getXGameCoord() + 1) + ", "
//						+ interactor.getYGameCoord());

			}
			if (orientation == Orientation.N
					&& (interactor.getYGameCoord() - 1) > -1) {
				interactee = WorldSystem.get().getMemberFromCoords(
						interactor.getXGameCoord(),
						(interactor.getYGameCoord() - 1));
//				System.out.println("Member type " + interactor.status
//						+ "  influencing above at "
//						+ interactor.getXGameCoord() + ", "
//						+ (interactor.getYGameCoord() - 1));

			}
			if (orientation == Orientation.S
					&& (interactor.getYGameCoord() + 1) < WorldSystem
							.get().getGameYCoords().size()) {
				interactee = WorldSystem.get().getMemberFromCoords(
						interactor.getXGameCoord(),
						(interactor.getYGameCoord() + 1));
//				System.out.println("Member type " + interactor.status
//						+ "  influencing below at "
//						+ interactor.getXGameCoord() + ", "
//						+ (interactor.getYGameCoord() + 1));
			}
			if (orientation == Orientation.W
					&& (interactor.getXGameCoord() - 1) > -1) {
				interactee = WorldSystem.get().getMemberFromCoords(
						interactor.getXGameCoord() - 1,
						(interactor.getYGameCoord()));
//				System.out.println("Member type " + interactor.status
//						+ " influencing to the left at "
//						+ (interactor.getXGameCoord() + 1) + ", "
//						+ interactor.getYGameCoord());
			}

			// Perform interaction
			if (interactee != null) {
				interact(interactor, interactee);
			}

		}

	}

	protected void interact(GameSprite interactor, GameSprite interactee) {

		if (this.interactionBehaviour != null) {
			this.interactionBehaviour.interact(interactor, interactee);
		}
	}
}
