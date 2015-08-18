package com.realrules.game.interact;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.realrules.game.main.CoordinateSystem;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.CoordinateSystem.Coordinates;

//import com.realrules.game.demo.DemoGame.Interaction;

public class AutonomousInteraction {
	public IInteraction interactionBehaviour = null;

	public void interactAutonomous(HeadSprite interactor, Group actorGroup) {

		// As long as interactor isn't neutral
		if (interactor.status != 0) {
			// Determine interactor's direction
			float facingAngle = interactor.getRotation();
			int direction = interactor.getDirection();

			HeadSprite interactee = null;

			// If facing towards the right
			if (direction == 1) {
				if (facingAngle == 0
						&& (interactor.getXGameCoord() + 1) < CoordinateSystem
								.get().getGameXCoords().size()) {
					// Get interactee by coordinates
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord() + 1,
							(interactor.getYGameCoord()));
					// System.out.println("Member type "+interactor.status+"  influencing to the right at "+(interactor.getXGameCoord()+1)+", "+interactor.getYGameCoord());

				}
				if (facingAngle == 90 && (interactor.getYGameCoord() - 1) > -1) {
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord(),
							(interactor.getYGameCoord() - 1));
					// System.out.println("Member type "+interactor.status+"  influencing above at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()-1));

				}
				if (facingAngle == 270
						&& (interactor.getYGameCoord() + 1) < CoordinateSystem
								.get().getGameYCoords().size()) {
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord(),
							(interactor.getYGameCoord() + 1));
					// System.out.println("Member type "+interactor.status+"  influencing below at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()+1));
				}
			}

			// If facing towards the left
			if (direction == 0) {
				if (facingAngle == 0 && (interactor.getXGameCoord() - 1) > -1) {
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord() - 1,
							(interactor.getYGameCoord()));
					// System.out.println("Member type "+interactor.status+" influencing to the left at "+(interactor.getXGameCoord()+1)+", "+interactor.getYGameCoord());
				}
				if (facingAngle == 90
						&& (interactor.getYGameCoord() + 1) < CoordinateSystem
								.get().getGameYCoords().size()) {
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord(),
							(interactor.getYGameCoord() + 1));
					// System.out.println("Member type "+interactor.status+" influencing above at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()+1));
				}
				if (facingAngle == 270 && (interactor.getYGameCoord() - 1) > -1) {
					interactee = CoordinateSystem.get().getMemberFromCoords(
							interactor.getXGameCoord(),
							(interactor.getYGameCoord() - 1));
					// System.out.println("Member type "+interactor.status+" influencing below at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()-1));
				}
			}

			// Perform interaction
			if (interactee != null) {
				// System.out.println("Influencing type "+interactee.status+"");
				interact(interactor, interactee);
			}

		}

	}

	public void interact(HeadSprite interactor, Group actorGroup,Coordinates orientation) {

		// As long as interactor isn't neutral
		if (interactor.status != 0) {
			HeadSprite interactee = null;

			// If facing towards the right
			if (orientation == Coordinates.E
					&& (interactor.getXGameCoord() + 1) < CoordinateSystem
							.get().getGameXCoords().size()) {
				// Get interactee by coordinates
				interactee = CoordinateSystem.get().getMemberFromCoords(
						interactor.getXGameCoord() + 1,
						(interactor.getYGameCoord()));
				System.out.println("Member type " + interactor.status
						+ "  influencing to the right at "
						+ (interactor.getXGameCoord() + 1) + ", "
						+ interactor.getYGameCoord());

			}
			if (orientation == Coordinates.N
					&& (interactor.getYGameCoord() - 1) > -1) {
				interactee = CoordinateSystem.get().getMemberFromCoords(
						interactor.getXGameCoord(),
						(interactor.getYGameCoord() - 1));
				System.out.println("Member type " + interactor.status
						+ "  influencing above at "
						+ interactor.getXGameCoord() + ", "
						+ (interactor.getYGameCoord() - 1));

			}
			if (orientation == Coordinates.S
					&& (interactor.getYGameCoord() + 1) < CoordinateSystem
							.get().getGameYCoords().size()) {
				interactee = CoordinateSystem.get().getMemberFromCoords(
						interactor.getXGameCoord(),
						(interactor.getYGameCoord() + 1));
				System.out.println("Member type " + interactor.status
						+ "  influencing below at "
						+ interactor.getXGameCoord() + ", "
						+ (interactor.getYGameCoord() + 1));
			}
			if (orientation == Coordinates.W
					&& (interactor.getXGameCoord() - 1) > -1) {
				interactee = CoordinateSystem.get().getMemberFromCoords(
						interactor.getXGameCoord() - 1,
						(interactor.getYGameCoord()));
				System.out.println("Member type " + interactor.status
						+ " influencing to the left at "
						+ (interactor.getXGameCoord() + 1) + ", "
						+ interactor.getYGameCoord());
			}

			// Perform interaction
			if (interactee != null) {
				interact(interactor, interactee);
			}

		}

	}

	protected void interact(HeadSprite interactor, HeadSprite interactee) {

		if (this.interactionBehaviour != null) {
			this.interactionBehaviour.interact(interactor, interactee);
		}
	}
}
