package com.realrules.touch;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.TouchAction;

public class InfluencerTouchAction extends TouchAction {

	private ArrayList<Integer> validXCoords = new ArrayList<Integer>();
	private ArrayList<Integer> validYCoords = new ArrayList<Integer>();
	private HeadSprite interacter;
	
	public InfluencerTouchAction() {}
	
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
				validXCoords.add(origY-1);
				validXCoords.add(origY);
			}
			else {
				validXCoords.add(origY+1);
				validXCoords.add(origY);
			}
		}
		else if(randVal >= 0.33 && randVal <= 0.66) {
			validXCoords.add(origY);
			if(CoordinateSystem.isValidYCoordinate(origY-1)) {
				validXCoords.add(origY-1);
			}
			else {
				validXCoords.add(origY+1);
			}
		}
		else {
			if(CoordinateSystem.isValidXCoordinate(origY+1)) {
				validXCoords.add(origY+1);
				validXCoords.add(origY);
			}
			else {
				validXCoords.add(origY-1);
				validXCoords.add(origY);
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
