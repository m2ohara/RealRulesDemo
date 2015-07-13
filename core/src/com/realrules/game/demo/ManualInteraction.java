package com.realrules.game.demo;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.interact.IManualInteraction;

//TODO: Test ManualInteraction
public class ManualInteraction {
	//Interacting
	HeadSprite interactor;
	private int hitCount = 0;
	HeadSprite lastHitActor = null;
	boolean invalidInteraction = false;
	private IManualInteraction manualInteraction = null;
	
	public ManualInteraction(IManualInteraction manualInteraction) {
		this.manualInteraction = manualInteraction;
	}
	
	public void interactHit(HeadSprite hitActor, boolean isFirst) {
		//If new actor is hit
		if((lastHitActor == null || !hitActor.equals(lastHitActor))) {
				
			if(hitActor.isActive) {
				
				//Flag actor as inactive for other interactions
				hitActor.isActive = false;
				
				//If first hit and is influenced actor
				if(isFirst && hitActor.status == 1) {	
					invalidInteraction = false;
					interactor = hitActor;
					
					float angle = hitActor.getRotation();
					angle = hitActor.getDirection() == 0 ? angle + 180f : angle;
					
					System.out.println("First follower hit facing "+angle);
				}
				//Neutral interactee
				else if(interactor != null && !isFirst && !invalidInteraction && interactor.behaviour.getInfluenceAmount() > hitCount && hitActor.status == 0) {
					if(validInteraction(hitActor)) {
						//Set previous hit actor to passive follower
//						setToMiddleFollower(lastHitActor);
						manualInteraction.setToMiddleFollower(lastHitActor);
						hitCount += 1;
						setToLastFollower(hitActor);
						//Update hit count
						ScoreState.addUserPoints(1);
					}
					else {
						invalidInteraction = true;
					}
				}	
				else {
					invalidInteraction = true;
				}

				hitActor.isActive = true;
				
				lastHitActor = hitActor;
			}
		}
	}
	
	public void reset() {
		interactor = null;
		hitCount = 0;
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
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
						isValid = true;
						System.out.println("Follower hit to the right");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
			}
			
			//If facing towards the left
			if(direction == 0) {
				if(facingAngle == 0) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == (CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX)+1) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)) {
						isValid = true;
						System.out.println("Follower hit to the left");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
			}
		}
		
		return isValid;

	}
	
	private void setToMiddleFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.CYAN);
		hitActor.status = 2;
	}
	
	private void setToLastFollower(HeadSprite hitActor) {
		if(ScoreState.validTouchAction()) {
			hitActor.setColor(Color.YELLOW);
		}
		else {
			hitActor.setColor(Color.GREEN);
		}
		hitActor.status = 1;
	}
}
