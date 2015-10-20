package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.act.IOnActing;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.InteractSprite;

public class GossiperInteractBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private float promoteOpposeProb = 0.5f;
	private Random rand = new Random();
	private float interactionStateLength = 3f;
	private int interactionStages = 3;
	private int origStatus;
	private InteractSprite interactSprite;
	
	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		
		//Influence if interactee is neutral and interactor isn't already interacting
		if(interactor.status != 4 && interactee.status == 0 && rand.nextFloat() > interactSuccess) {
			origStatus = interactor.status;
			interactor.status = 4;
			interactee.isActive = false;
			interactSprite = new InteractSprite(interactionStateLength, interactionStages, interactor);
			interactSprite.setAction();

		}
		//Perform interaction
		if(interactSprite != null && interactSprite.isComplete() == true) {
			interactor.status = origStatus;
			interactee.isActive = true;
			setInteractionResult(interactee);
		}
		
	}
	
	private void setInteractionResult(HeadSprite interactee) {
		
		if(interactee.status == 0 && interactee.isActive == true) {
			//Oppose
			if(rand.nextFloat() > promoteOpposeProb) {
				interactee.status = 3;
				setInfluencedSprite(interactee, 1);
			}
			//Promote
			else {
				interactee.status = 2;
				setInfluencedSprite(interactee, 0);
			}
		}
	}
	
	private void setInfluencedSprite(HeadSprite interactee, int influenceType) {
		
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(influenceType));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		handSign.setTouchable(Touchable.disabled);
		
		GameProperties.get().addActorToStage(handSign);
	}
	
//	private void setConnectorSprite(HeadSprite interactor, int influenceType) {
//		Coordinates direction = CoordinateSystem.getCoordDirection(interactor.getDirection(), (int)interactor.getRotation());
//		
//		int influenceSprite = influenceType == 0 ? 0 : 1;
//		
//		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(influenceSprite ));
//
//		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
//		connector.setPosition(interactor.getStartingX() - 20, interactor.getStartingY() -22);
//		connector.setTouchable(Touchable.disabled);
//		
//		if(direction == Coordinates.E) {
//			connector.rotateBy(270);
//		}
//		else if(direction == Coordinates.S) {
//			connector.rotateBy(180);
//		}
//		else if(direction == Coordinates.W) {
//			connector.rotateBy(90);
//		}
//		
//		
//		GameProperties.get().addActorToStage(connector);
//	}
	
}
