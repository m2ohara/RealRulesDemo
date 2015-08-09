package com.realrules.game.demo;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.ManualSupporterInteraction;

public class InfluencerTouchAction extends TouchAction {

	private ArrayList<Integer> validXCoords = new ArrayList<Integer>();
	private ArrayList<Integer> validYCoords = new ArrayList<Integer>();
	private HeadSprite interacter;
	private IManualInteraction manInteraction = null;
	
	public InfluencerTouchAction(int x, int y, IManualInteraction manInteraction) {
		this.manInteraction = new ManualSupporterInteraction();
		this.setInteractorX(x);
		this.setInteractorY(y);
		this.setInteractorDir(CoordinateSystem.getCoordDirection(0, 0));
	}
	
	@Override
	protected void generateValidInteractees() {
		
		if(this.getInteractorDir() != null) {
			int origX = this.getInteractorX();
			int origY = this.getInteractorY();
			
			interacter = CoordinateSystem.get().getMemberFromCoords(origX, origY);
			
			Random rand = new Random();
			
			//Determine direction
			if(this.getInteractorDir() == Coordinates.N) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidYCoordinate(origY-1)) {
					validYCoords.add(origY-1);
					validYCoords.add(origY-1);
					
					float randVal = rand.nextFloat();
					setValidXCoords(randVal, origX);

				}
			}
			
			if(this.getInteractorDir() == Coordinates.E) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidXCoordinate(origX+1)) {
					validXCoords.add(origX+1);
					validXCoords.add(origX+1);
					
					float randVal = rand.nextFloat();
					setValidYCoords(randVal, origY);
				}
			}
			
			if(this.getInteractorDir() == Coordinates.S) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidYCoordinate(origY+1)) {
					validYCoords.add(origY+1);
					validYCoords.add(origY+1);
					
					float randVal = rand.nextFloat();
					setValidXCoords(randVal, origX);
				}
			}
			
			if(this.getInteractorDir() == Coordinates.W) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidXCoordinate(origX-1)) {
					validXCoords.add(origX-1);
					validXCoords.add(origX-1);
					
					float randVal = rand.nextFloat();
					setValidYCoords(randVal, origY);
				} 
			}
		}
		
	}
	
	private void setValidXCoords(float randVal, int origX) {
		if(randVal < 0.33) {
			if(CoordinateSystem.isValidXCoordinate(origX-1)) {
				validXCoords.add(origX-1);
				validXCoords.add(origX);
			}
			else {
				validXCoords.add(origX+1);
				validXCoords.add(origX);
			}
		}
		else if(randVal >= 0.33 && randVal <= 0.66) {
			validXCoords.add(origX);
			if(CoordinateSystem.isValidXCoordinate(origX-1)) {
				validXCoords.add(origX-1);
			}
			else {
				validXCoords.add(origX+1);
			}
		}
		else {
			if(CoordinateSystem.isValidXCoordinate(origX+1)) {
				validXCoords.add(origX+1);
				validXCoords.add(origX);
			}
			else {
				validXCoords.add(origX-1);
				validXCoords.add(origX);
			}
		}
	}
	
	private void setValidYCoords(float randVal, int origY) {
		if(randVal < 0.33) {
			if(CoordinateSystem.isValidYCoordinate(origY-1)) {
				validYCoords.add(origY-1);
				validYCoords.add(origY);
			}
			else {
				validYCoords.add(origY+1);
				validYCoords.add(origY);
			}
		}
		else if(randVal >= 0.33 && randVal <= 0.66) {
			validYCoords.add(origY);
			if(CoordinateSystem.isValidYCoordinate(origY-1)) {
				validYCoords.add(origY-1);
			}
			else {
				validYCoords.add(origY+1);
			}
		}
		else {
			if(CoordinateSystem.isValidXCoordinate(origY+1)) {
				validYCoords.add(origY+1);
				validYCoords.add(origY);
			}
			else {
				validYCoords.add(origY-1);
				validYCoords.add(origY);
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
				HeadSprite actor = CoordinateSystem.get().getMemberFromCoords(validXCoords.get(i), validYCoords.get(i));
				if(i == validXCoords.size()-1) {
					setToLastFollower(actor);
				}
				else {
					manInteraction.setToMiddleFollower(actor);
//					setConnectorSprite(actor);
				}
				
			}
		}
		
	}
	
	private void setToLastFollower(HeadSprite actor) {
		actor.setColor(Color.GREEN);
		actor.status = 1;
	}
	
	private void setConnectorSprite(HeadSprite lastHitActor) {
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(1));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(lastHitActor.getStartingX() - 20, lastHitActor.getStartingY() -22);
		
		if(this.getInteractorDir() == Coordinates.E) {
			connector.rotateBy(270);
		}
		else if(this.getInteractorDir() == Coordinates.S) {
			connector.rotateBy(180);
		}
		else if(this.getInteractorDir() == Coordinates.W) {
			connector.rotateBy(90);
		}
		
		
		GameProperties.get().addActorToStage(connector);
	}


}
