package com.realrules.game.touch;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.interact.OpposerInteractionType;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.state.GameScoreState;

public class GossiperTouchAction extends TouchAction{
	
	private GameSprite interacter;
	private IInteractionType manInteraction = null;
	private int spriteType;
	
	public GossiperTouchAction(IInteractionType manInteraction, int x, int y) {
		super(x, y);
		this.manInteraction = manInteraction;
		this.setInteractorDir(WorldSystem.getCoordDirection(0, 0));
		
		spriteType = manInteraction instanceof OpposerInteractionType ? 0 : 1; 
	}

	//Two members ahead of interactor are valid
	@Override
	protected void generateValidInteractees() {
		
		if(this.getInteractorDir() != null) {
			int origX = this.getInteractorX();
			int origY = this.getInteractorY();
			
			interacter = WorldSystem.get().getMemberFromCoords(origX, origY);
			
			//Determine direction
			if(this.getInteractorDir() == Orientation.N) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidYCoordinate(origY-1) && WorldSystem.get().getMemberFromCoords(origX, origY-1) != null) {
					validYCoords.add(origY-1);
					validXCoords.add(origX);
				}
				if(WorldSystem.get().isValidYCoordinate(origY-2) && WorldSystem.get().getMemberFromCoords(origX, origY-2) != null) {
					validYCoords.add(origY-2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.E) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidXCoordinate(origX+1) && WorldSystem.get().getMemberFromCoords(origX+1, origY) != null) {
					validXCoords.add(origX+1);
					validYCoords.add(origY);
				}
				if(WorldSystem.get().isValidXCoordinate(origX+2) && WorldSystem.get().getMemberFromCoords(origX+2, origY) != null) {
					validXCoords.add(origX+2);
					validYCoords.add(origY);
				}
			}
			
			if(this.getInteractorDir() == Orientation.S) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidYCoordinate(origY+1) && WorldSystem.get().getMemberFromCoords(origX, origY+1) != null) {
					validYCoords.add(origY+1);
					validXCoords.add(origX);
				}
				if(WorldSystem.get().isValidYCoordinate(origY+2) && WorldSystem.get().getMemberFromCoords(origX, origY+2) != null) {
					validYCoords.add(origY+2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.W) {
				//Set related coordinates for valid interactees
				if(WorldSystem.get().isValidXCoordinate(origX-1) && WorldSystem.get().getMemberFromCoords(origX-1, origY) != null) {
					validXCoords.add(origX-1);
					validYCoords.add(origY);
				}
				if(WorldSystem.get().isValidXCoordinate(origX-2) && WorldSystem.get().getMemberFromCoords(origX-2, origY) != null) {
					validXCoords.add(origX-2);
					validYCoords.add(origY);
				} 
			}
		}

		
		
	}

	@Override
	public void interact() {
		
		if(isValidInteractor()) {
			
			//Generate current crowd members that can be influenced
			generateValidInteractees();
			
			if(validXCoords.size() > 0) {
				manInteraction.setToMiddleFollower(interacter);
				setConnectorSprite(interacter);
				for(int i = 0; i < validXCoords.size(); i++) {
					GameSprite actor = WorldSystem.get().getMemberFromCoords(validXCoords.get(i), validYCoords.get(i));
					if(i == validXCoords.size()-1) {
						setToLastFollower(actor);
					}
					else {
						manInteraction.setToMiddleFollower(actor);
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
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(spriteType));

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
