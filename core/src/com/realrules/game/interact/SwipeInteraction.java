package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.interact.individual.IndividualInteraction;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;
import com.realrules.game.main.SwipeInteractSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.state.GameScoreState;
import com.realrules.game.state.PlayerState;

public class SwipeInteraction {
	//Interacting
	GameSprite interactor;
	private int hitCount = 0;
	GameSprite lastHitActor = null;
	boolean invalidInteraction = false;
	private IInteractionType interactionType = null;
	private int connectorSprite;
	private float interactionStateLength = 3f;
	private int interactionStages = 3;
	//TODO: Clean up orientation logic
	SwipeInteractSprite firstInteraction = null;
//	private Orientation orientation = null;
	private Array<Actor> connectors = new Array<Actor>();

	public SwipeInteraction(IInteractionType interactionType, int connectorSprite) {
		this.interactionType = interactionType;
		this.connectorSprite = connectorSprite == 0 ? 1  : 0;
	}

	public boolean interactHit(GameSprite hitActor, boolean isFirst) {
		
		//If new actor is hit
		if((lastHitActor == null || !hitActor.equals(lastHitActor))) {

			if(hitActor.isActive) {

				//If first hit and is the current influenced actor
				if(isFirst && hitActor.interactStatus == Status.SELECTED) {	
					invalidInteraction = false;
					interactor = hitActor;

					System.out.println("First follower hit facing "+hitActor.getOrientation());
				}
				//If next
				else if(interactor != null && !isFirst && !invalidInteraction && PlayerState.get().getInfluenceLimit() > hitCount && hitActor.interactStatus == Status.NEUTRAL && isValidInteraction(hitActor)) {
						lastHitActor.isActive = false;
						hitActor.isActive = false;
						interact(lastHitActor, hitActor );
						
						setConnectorSprite(lastHitActor);
						hitCount += 1;
						GameScoreState.addUserPoints(1);
						System.out.println("Next follower hit facing "+hitActor.getOrientation());

				}	
				else {
					System.out.println("No follower hit");
					hitActor.isActive = true;
					lastHitActor = null;
					return false;
				}
				
				lastHitActor = hitActor;
				return true;
			}
		}
		
		return false;
	}
	
	private void interact(GameSprite interactor, GameSprite interactee) {

		//Influence if interactee is neutral and interactor isn't already interacting
		if(interactee.interactStatus == Status.NEUTRAL) {
			
			interactee.interactStatus = Status.INFLUENCED;
			interactee.interactorType = InteractorType.Last;
			
			if(firstInteraction == null) {
				System.out.println("Assigning first interaction");
				interactor.interactorType = InteractorType.First;
				firstInteraction = new SwipeInteractSprite(interactionStateLength, interactionStages, interactor, interactee, new IndividualInteraction());
			}
			else {
				System.out.println("Assigning next interaction");
				interactor.interactStatus = Status.INFLUENCED;
				interactor.interactorType = InteractorType.Intermediate;
				new SwipeInteractSprite(interactionStateLength, interactionStages, interactor, interactee, new IndividualInteraction());
			}

		}
	}

	public void reset() {
		if(firstInteraction != null) {firstInteraction.startInteraction();}
		interactor = null;
		hitCount = 0;
		lastHitActor = null;
		firstInteraction = null;
		removeConnectors();
	}

	public boolean isValidInteraction(GameSprite hitActor) {

		boolean isValid = false;

		if(lastHitActor != null && hitActor.interactStatus == Status.NEUTRAL) {

			if(lastHitActor.getOrientation() == Orientation.E) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX)-1) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
					isValid = true;
//					System.out.println("Follower hit to the right. Last Hit x : "+WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX)+", Hit X "+WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX));
				}
			}
			else if(lastHitActor.getOrientation() == Orientation.N) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
					isValid = true;
//					System.out.println("Follower hit above");
				}

			}
			else if(lastHitActor.getOrientation() == Orientation.S) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
					isValid = true;
//					System.out.println("Follower hit below");
				}
			}
			else if(lastHitActor.getOrientation() == Orientation.W) {
				if(WorldSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (WorldSystem.get().getGameXCoords().indexOf(hitActor.startingX)+1) 
						&& WorldSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  WorldSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
					isValid = true;
//					System.out.println("Follower hit to the left");
				}
			}
		}

		return isValid;

	}

	private void setConnectorSprite(GameSprite lastHitActor) {

		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(connectorSprite));
		connectors.add(connector);

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(lastHitActor.getStartingX() - 20, lastHitActor.getStartingY() -22);

		if(lastHitActor.getOrientation() == Orientation.E) {
			connector.rotateBy(270);
		}
		else if(lastHitActor.getOrientation() == Orientation.S) {
			connector.rotateBy(180);
		}
		else if(lastHitActor.getOrientation() == Orientation.W) {
			connector.rotateBy(90);
		}


		GameProperties.get().addActorToStage(connector);
	}
	
	private void removeConnectors() {
		GameProperties.get().getStage().getActors().removeAll(connectors, false);
	}

}
