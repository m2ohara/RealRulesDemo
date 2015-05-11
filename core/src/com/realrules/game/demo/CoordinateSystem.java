package com.realrules.game.demo;

public class CoordinateSystem {
	
	private static int systemWidth = 3;
	private static int systemHeight = 4;
	public static enum Coordinates {N, E, S, W}
	private static CoordinateSystem instance;
	
	public CoordinateSystem get() {
		if(instance == null) {
			return new CoordinateSystem();
		}
		return instance;
	}
	
	private CoordinateSystem() {
		
	}
	
	public static int getSystemWidth() {
		return systemWidth;
	}

	public static int getSystemHeight() {
		return systemHeight;
	}
	
	//Determine direction object is facing
	public static Coordinates getCoordinates(int dir, int rotation) {
		
		//TODO: dev logic for getting coordinates
		if(dir == 0){

		}
		return null;
			
		
	}
	
	public static boolean isValidYCoordinate(int coordinate) {
		if(coordinate <= systemHeight)
		{	
			return true;
		}
		return false;
	}
		
	
	public static boolean isValidXCoordinate(int coordinate) {
		if(coordinate <= systemWidth)
		{	
			return true;
		}
		return false;
	}

}
