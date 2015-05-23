package com.realrules.game.demo;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.DemoGame.HeadSprite;

public class ManualInteraction {
	//Interacting
	HeadSprite interactor;
	ArrayList<HeadSprite> interactees = new ArrayList<HeadSprite>();
	HeadSprite lastHitActor = null;
	boolean invalidInteraction = false;
	
	public ManualInteraction() {
		
	}
	
	public void interactHit(HeadSprite hitActor, boolean isFirst) {
		//If new actor is hit
		if((lastHitActor == null || !hitActor.equals(lastHitActor))) {
				
				//If first hit and is influenced actor
				if(isFirst && hitActor.status == 1 && hitActor.isActive) {	
					invalidInteraction =false;
					interactor = hitActor;
//					setToFollower(hitActor);
					
					float angle = hitActor.getRotation();
					angle = hitActor.getDirection() == 0 ? angle + 180f : angle;
					
					System.out.println("First follower hit facing "+angle);
				}
				//Neutral interactee
				else if(interactor != null && !invalidInteraction && !isFirst && interactor.behaviour.getInfluenceAmount() > interactees.size() && hitActor.status == 0) {
					if(validInteraction(hitActor)) {
						//Set previous hit actor to untouchable
						setToMiddleFollower(lastHitActor);
						interactees.add(hitActor);
						setToLastFollower(hitActor);
					}
					else {
						invalidInteraction = true;
						System.out.println("Invalid hit");
					}
				}		
				
				hitActor.isActive = true;
				
				lastHitActor = hitActor;
			}
	}
	
	public void reset() {
		interactor = null;
		interactees.clear();
		lastHitActor = null;
	}
	
	private boolean validInteraction(HeadSprite hitActor) {
		
		boolean isValid = false;
		
		if(hitActor.status == 0) {
			//Get lastHitActor's facing angle
			float facingAngle = lastHitActor.getRotation();
			int direction = lastHitActor.getDirection();
			
			//If facing towards the right
			if(direction == 1) {
				if(facingAngle == 0) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX)-1) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)) {
						isValid = true;
						System.out.println("Follower hit to the right");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)+1)) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)-1)) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
			}
			
			//If facing towards the left
			if(direction == 0) {
				if(facingAngle == 0) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX)+1) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)) {
						isValid = true;
						System.out.println("Follower hit to the left");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)-1)) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().gameYCoords.indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().gameYCoords.indexOf(hitActor.startingY)+1)) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
			}
		}
		
		return isValid;

	}
	
	private void setToFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.CYAN);
		hitActor.status = 1;
	}
	
	private void setToMiddleFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.CYAN);
		hitActor.status = 2;
	}
	
	private void setToLastFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.GREEN);
		hitActor.status = 1;
	}
}
