package com.realrules.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.interact.IManualInteraction;

//TODO: Test ManualInteraction
public class ManualInteraction {
	//Interacting
	HeadSprite interactor;
	private int hitCount = 0;
	HeadSprite lastHitActor = null;
	boolean invalidInteraction = false;
	private IManualInteraction manualInteraction = null;
	private int connectorSprite;
	private Coordinates coordinate;
	
	public ManualInteraction(IManualInteraction manualInteraction, int connectorSprite) {
		this.manualInteraction = manualInteraction;
		this.connectorSprite = connectorSprite == 0 ? 1  : 0;
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
						setConnectorSprite(lastHitActor);
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
						coordinate = Coordinates.E;
						System.out.println("Follower hit to the right");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
						isValid = true;
						coordinate = Coordinates.N;
						System.out.println("Follower hit above");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
						isValid = true;
						coordinate = Coordinates.S;
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
						coordinate = Coordinates.W;
						System.out.println("Follower hit to the left");
					}
				}
				if(facingAngle == 90) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)-1)) {
						isValid = true;
						coordinate = Coordinates.S;
						System.out.println("Follower hit below");
					}
				}
				if(facingAngle == 270) {
					if(CoordinateSystem.get().getGameXCoords().indexOf(lastHitActor.startingX) == CoordinateSystem.get().getGameXCoords().indexOf(hitActor.startingX) 
							&& CoordinateSystem.get().getGameYCoords().indexOf(lastHitActor.startingY) ==  (CoordinateSystem.get().getGameYCoords().indexOf(hitActor.startingY)+1)) {
						isValid = true;
						coordinate = Coordinates.N;
						System.out.println("Follower hit above");
					}
				}
			}
		}
		
		return isValid;

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
	
	private void setConnectorSprite(HeadSprite lastHitActor) {
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(connectorSprite));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(lastHitActor.getStartingX() - 20, lastHitActor.getStartingY() -22);
		
		if(coordinate == Coordinates.E) {
			connector.rotateBy(270);
		}
		else if(coordinate == Coordinates.S) {
			connector.rotateBy(180);
		}
		else if(coordinate == Coordinates.W) {
			connector.rotateBy(90);
		}
		
		
		GameProperties.get().addActorToStage(connector);
	}
}
