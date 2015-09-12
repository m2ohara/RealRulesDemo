package com.realrules.game.touch;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.ManualOpposerInteraction;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.WorldSystem.Orientation;

public class DeceiverTouchAction extends TouchAction {

	private ArrayList<Integer> validXCoords = new ArrayList<Integer>();
	private ArrayList<Integer> validYCoords = new ArrayList<Integer>();
	private HeadSprite interacter;
	private IManualInteraction manInteraction = null;
	
	public DeceiverTouchAction(IManualInteraction manInteraction) {
		this.manInteraction = new ManualOpposerInteraction();
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
				if(WorldSystem.isValidYCoordinate(origY-1)) {
					validYCoords.add(origY-1);
					validXCoords.add(origX);
				}
				if(WorldSystem.isValidYCoordinate(origY-2)) {
					validYCoords.add(origY-2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.E) {
				//Set related coordinates for valid interactees
				if(WorldSystem.isValidXCoordinate(origX+1)) {
					validXCoords.add(origX+1);
					validYCoords.add(origY);
				}
				if(WorldSystem.isValidXCoordinate(origX+2)) {
					validXCoords.add(origX+2);
					validYCoords.add(origY);
				}
			}
			
			if(this.getInteractorDir() == Orientation.S) {
				//Set related coordinates for valid interactees
				if(WorldSystem.isValidYCoordinate(origY+1)) {
					validYCoords.add(origY+1);
					validXCoords.add(origX);
				}
				if(WorldSystem.isValidYCoordinate(origY+2)) {
					validYCoords.add(origY+2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Orientation.W) {
				//Set related coordinates for valid interactees
				if(WorldSystem.isValidXCoordinate(origX-1)) {
					validXCoords.add(origX-1);
					validYCoords.add(origY);
				}
				if(WorldSystem.isValidXCoordinate(origX-2)) {
					validXCoords.add(origX-2);
					validYCoords.add(origY);
				} 
			}
		}

		
		
	}

	@Override
	public void interact() {
		//Generate current crowd members that can be influenced
		generateValidInteractees();
		
		if(validXCoords.size() > 0) {
			manInteraction.setToMiddleFollower(interacter);
			setConnectorSprite(interacter);
			for(int i = 0; i < validXCoords.size(); i++) {
				HeadSprite actor = WorldSystem.get().getMemberFromCoords(validXCoords.get(i), validYCoords.get(i));
				if(i == validXCoords.size()-1) {
					setToLastFollower(actor);
				}
				else {
					manInteraction.setToMiddleFollower(actor);
					setConnectorSprite(actor);
				}
				
			}
		}
		
	}
		
	private void setToLastFollower(HeadSprite actor) {
		actor.setColor(Color.YELLOW);
		actor.status = 1;
	}
	
	private void setConnectorSprite(HeadSprite lastHitActor) {
		
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
