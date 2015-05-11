package com.realrules.game.demo;

import java.util.ArrayList;

import com.realrules.game.demo.CoordinateSystem.Coordinates;

public class GossiperTouchAction extends TouchAction{
	
	private ArrayList<Integer> validXCoords = new ArrayList<Integer>();
	private ArrayList<Integer> validYCoords = new ArrayList<Integer>();
	
	public GossiperTouchAction() {
		
	}

	@Override
	public void generateValidInteractees(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
		
		//Two members ahead of interactor are valid
		int origX = this.getInteractorX();
		int origY = this.getInteractorY();
		
		//Determine direction
		if(this.getInteractorDir() == Coordinates.N) {
			//Set related coordinates for valid interactees
			if(CoordinateSystem.isValidYCoordinate(origY-1))
				validYCoords.add(origY-1);
			if(CoordinateSystem.isValidYCoordinate(origY-2))
				validYCoords.add(origY-2);
		}
		
		if(this.getInteractorDir() == Coordinates.E) {
			//Set related coordinates for valid interactees
			if(CoordinateSystem.isValidXCoordinate(origX+1))
				validXCoords.add(origX+1);
			if(CoordinateSystem.isValidXCoordinate(origX+2))
				validXCoords.add(origX+2);
		}
		
		if(this.getInteractorDir() == Coordinates.S) {
			//Set related coordinates for valid interactees
			if(CoordinateSystem.isValidYCoordinate(origY+1))
				validYCoords.add(origY+1);
			if(CoordinateSystem.isValidYCoordinate(origY+2))
				validYCoords.add(origY+2);
		}
		
		if(this.getInteractorDir() == Coordinates.W) {
			//Set related coordinates for valid interactees
			if(CoordinateSystem.isValidXCoordinate(origX-1))
				validXCoords.add(origX-1);
			if(CoordinateSystem.isValidXCoordinate(origX-2))
				validXCoords.add(origX-2);
		}

		
		
	}
	
	
	

}
