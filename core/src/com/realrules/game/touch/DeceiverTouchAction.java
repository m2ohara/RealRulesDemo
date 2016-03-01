package com.realrules.game.touch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.interact.OpposerInteractionType;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.state.GameScoreState;

public class DeceiverTouchAction extends TouchAction {

	private IInteractionType manInteraction = null;
	
	public DeceiverTouchAction(IInteractionType manInteraction, int x, int y) {
		super( x, y);
		this.manInteraction = new OpposerInteractionType();
		this.setInteractorDir(WorldSystem.getCoordDirection(0, 0));
	}

	//Two members ahead of interactor are valid
	@Override
	protected void generateValidInteractees() {
		
		if(this.getInteractorDir() != null) {
			int origX = this.getInteractorX();
			int origY = this.getInteractorY();
			
			interactor = WorldSystem.get().getMemberFromCoords(origX, origY);
			
			//Determine direction
			if(this.getInteractorDir() == Orientation.N) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidYCoordinate(origY-1)) {
					validYCoords.add(origY-1);
					validXCoords.add(origX);
				}
				if(WorldSystem.get().isValidYCoordinate(origY-2)) {
					validYCoords.add(origY-2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.E) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidXCoordinate(origX+1)) {
					validXCoords.add(origX+1);
					validYCoords.add(origY);
				}
				if(WorldSystem.get().isValidXCoordinate(origX+2)) {
					validXCoords.add(origX+2);
					validYCoords.add(origY);
				}
			}
			
			if(this.getInteractorDir() == Orientation.S) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidYCoordinate(origY+1)) {
					validYCoords.add(origY+1);
					validXCoords.add(origX);
				}
				if(WorldSystem.get().isValidYCoordinate(origY+2)) {
					validYCoords.add(origY+2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.W) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidXCoordinate(origX-1)) {
					validXCoords.add(origX-1);
					validYCoords.add(origY);
				}
				if(WorldSystem.get().isValidXCoordinate(origX-2)) {
					validXCoords.add(origX-2);
					validYCoords.add(origY);
				} 
			}
		}

		removeEmptyCoordinates();
		
	}

	@Override
	public void onAction() {
		
		if(isSelectedInteractor()) {
			//Generate current crowd members that can be influenced
			generateValidInteractees();
			
			if(validXCoords.size() > 0) {
				manInteraction.setStatus();
				setConnectorSprite(interactor);
				for(int i = 0; i < validXCoords.size(); i++) {
					GameSprite actor = WorldSystem.get().getMemberFromCoords(validXCoords.get(i), validYCoords.get(i));
					if(i == validXCoords.size()-1) {
						setToLastFollower(actor);
					}
					else {
						manInteraction.setStatus();
						setConnectorSprite(actor);
					}
					
				}
			}
			
			GameScoreState.resetUserPoints();
		}
		
	}
		
	private void setToLastFollower(GameSprite actor) {
		actor.setColor(Color.YELLOW);
		actor.status = 1;
	}
	
	private void setConnectorSprite(GameSprite lastHitActor) {
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(0));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(lastHitActor.getStartingX() - 20, lastHitActor.getStartingY() -22);
		
		if(this.getInteractorDir() == Orientation.E) {
			connector.rotateBy(270);
		}
		else if(this.getInteractorDir() == Orientation.S) {
			connector.rotateBy(180);
		}
		else if(this.getInteractorDir() == Orientation.W) {
			connector.rotateBy(90);
		}
		
		
		GameProperties.get().addActorToStage(connector);
	}

}
