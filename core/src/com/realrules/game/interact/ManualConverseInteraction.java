package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.ManualInteractSprite;
import com.realrules.game.main.ScoreState;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;

public class ManualConverseInteraction {
	//Interacting
	HeadSprite interactor;
	private int hitCount = 0;
	HeadSprite lastHitActor = null;
	boolean invalidInteraction = false;
	private IManualInteraction manualInteraction = null;
	private int connectorSprite;
	private Orientation coordinate;

	public ManualConverseInteraction(IManualInteraction manualInteraction, int connectorSprite) {
		this.manualInteraction = manualInteraction;
		this.connectorSprite = connectorSprite == 0 ? 1  : 0;
	}

	public void interactHit(HeadSprite hitActor, boolean isFirst) {
		//If new actor is hit
		if((lastHitActor == null || !hitActor.equals(lastHitActor))) {

			if(hitActor.isActive) {

				//Flag actor as inactive for other interactions

				//If first hit and is the current influenced actor
				if(isFirst && hitActor.status == 1) {	
					invalidInteraction = false;
					interactor = hitActor;

					System.out.println("First follower hit facing "+hitActor.getOrientation());
				}
				//Interactee
				else if(interactor != null && !isFirst && !invalidInteraction && interactor.behaviour.getInfluenceAmount() > hitCount && hitActor.status == 0) {
					if(validInteraction(hitActor, lastHitActor.getOrientation())) {
						hitActor.isActive = false;
						//Set previous hit actor to passive follower
						setConnectorSprite(lastHitActor);
						lastHitActor.isManualInteractor = true;
						interact(lastHitActor, hitActor );
						System.out.println("Influencing interactee at "+hitActor.getXGameCoord()+", "+hitActor.getYGameCoord());
						hitCount += 1;
						//Update hit count
						ScoreState.addUserPoints(1);
					}
					else {
						invalidInteraction = true;
						hitActor.isActive = true;
					}
				}	
				else {
					invalidInteraction = true;
					hitActor.isActive = true;
				}

				if(invalidInteraction)
					hitActor.isActive = true;

				lastHitActor = hitActor;
				
			}
		}
	}

	public void reset() {
		interactor = null;
		hitCount = 0;
		lastHitActor = null;

	}

	private boolean validInteraction(HeadSprite hitActor, Orientation swipeDirection) {

		boolean isValid = false;

		if(hitActor.status == 0) {

			coordinate = swipeDirection;
			if(swipeDirection == Orientation.E) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX)-1) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
					isValid = true;
					System.out.println("Follower hit to the right");
				}
			}
			else if(swipeDirection == Orientation.N) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
					isValid = true;
					System.out.println("Follower hit above");
				}

			}
			else if(swipeDirection == Orientation.S) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
					isValid = true;
					System.out.println("Follower hit below");
				}
			}
			else if(swipeDirection == Orientation.W) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX)+1) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
					isValid = true;
					coordinate = Orientation.W;
					System.out.println("Follower hit to the left");
				}
			}
		}

		return isValid;

	}

//	private void setToLastFollower(HeadSprite hitActor) {
//		if(ScoreState.validTouchAction()) {
//			hitActor.setColor(Color.ORANGE);
//		}
//		else {
//			hitActor.setColor(Color.YELLOW);
//		}
////		hitActor.status = 1;
//	}

	private void setConnectorSprite(HeadSprite lastHitActor) {

		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(connectorSprite));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(lastHitActor.getStartingX() - 20, lastHitActor.getStartingY() -22);

		if(coordinate == Orientation.E) {
			connector.rotateBy(270);
		}
		else if(coordinate == Orientation.S) {
			connector.rotateBy(180);
		}
		else if(coordinate == Orientation.W) {
			connector.rotateBy(90);
		}


		GameProperties.get().addActorToStage(connector);
	}
	
	public void interact(HeadSprite interactor, HeadSprite interactee) {

		//Influence if interactee is neutral and interactor isn't already interacting
		if(interactee.status == 0) {
			if(interactor.status == 1) {
				interactor.isActive = true;
			}
			new ManualInteractSprite(2f, 3, interactor, interactee);

		}
	}
}
