package com.realrules.game.demo;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.CoordinateSystem.Coordinates;

public class GossiperTouchAction extends TouchAction{
	
	private ArrayList<Integer> validXCoords = new ArrayList<Integer>();
	private ArrayList<Integer> validYCoords = new ArrayList<Integer>();
	private HeadSprite interacter;
	
	public GossiperTouchAction() {
	}

	//Two members ahead of interactor are valid
	@Override
	protected void generateValidInteractees() {
		
		if(this.getInteractorDir() != null) {
			int origX = this.getInteractorX();
			int origY = this.getInteractorY();
			
			interacter = CoordinateSystem.get().getMemberFromCoords(origX, origY);
			
			//Determine direction
			if(this.getInteractorDir() == Coordinates.N) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidYCoordinate(origY-1)) {
					validYCoords.add(origY-1);
					validXCoords.add(origX);
				}
				if(CoordinateSystem.isValidYCoordinate(origY-2)) {
					validYCoords.add(origY-2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Coordinates.E) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidXCoordinate(origX+1)) {
					validXCoords.add(origX+1);
					validYCoords.add(origY);
				}
				if(CoordinateSystem.isValidXCoordinate(origX+2)) {
					validXCoords.add(origX+2);
					validYCoords.add(origY);
				}
			}
			
			if(this.getInteractorDir() == Coordinates.S) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidYCoordinate(origY+1)) {
					validYCoords.add(origY+1);
					validXCoords.add(origX);
				}
				if(CoordinateSystem.isValidYCoordinate(origY+2)) {
					validYCoords.add(origY+2);
					validXCoords.add(origX);
				}
			}
			
			if(this.getInteractorDir() == Coordinates.W) {
				//Set related coordinates for valid interactees
				if(CoordinateSystem.isValidXCoordinate(origX-1)) {
					validXCoords.add(origX-1);
					validYCoords.add(origY);
				}
				if(CoordinateSystem.isValidXCoordinate(origX-2)) {
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
			setToMiddleFollower(interacter);
			for(int i = 0; i < validXCoords.size(); i++) {
				HeadSprite actor = CoordinateSystem.get().getMemberFromCoords(validXCoords.get(i), validYCoords.get(i));
				if(i == validXCoords.size()-1) {
					setToLastFollower(actor);
				}
				else {
					setToMiddleFollower(actor);
				}
				
			}
		}
		
	}
	
	private void setToMiddleFollower(HeadSprite actor) {
		actor.setColor(Color.CYAN);
		actor.status = 2;
	}
	
	private void setToLastFollower(HeadSprite actor) {

		actor.setColor(Color.GREEN);
		actor.status = 1;
	}
	
	
	

}
